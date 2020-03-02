package com.toucanwalletdemo.ui.components.login.mvi

import android.content.Context
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.LoginData
import com.toucanwalletdemo.data.models.ResetPinData
import com.toucanwalletdemo.data.repositories.RegisterRewardRepository
import com.toucanwalletdemo.data.repositories.UserRepository
import com.toucanwalletdemo.ui.base.mvi.MviActionsProcessor
import com.toucanwalletdemo.ui.base.mvi.SchedulersProvider
import com.toucanwalletdemo.ui.components.login.mvi.LogInAction.LoginUserAction
import com.toucanwalletdemo.ui.components.login.mvi.LogInResult.*
import com.toucanwalletdemo.utils.extensions.asObservable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class LogInActionProcessor: MviActionsProcessor<LogInAction, LogInResult>(), KoinComponent {

    private val userRepository: UserRepository by inject()
    private val schedulersProvider: SchedulersProvider by inject()
    private val registerRewardRepository: RegisterRewardRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<LogInAction>) = listOf(
        shared.connect(loginUserActionProcessor),
        shared.connect(changePinActionProcessor)
    )

    private val changePinActionProcessor = ObservableTransformer<LogInAction.ResetPinAction, LogInResult> { action ->
        action.flatMap { data ->
            when {
                data.identifier.isBlank() -> Error(context.getString(R.string.validation_error_username_email_empty)).asObservable()
                else -> {
                    userRepository.resetPin(ResetPinData(data.identifier))
                        .subscribeOn(schedulersProvider.subscriptionScheduler())
                        .observeOn(schedulersProvider.observableScheduler())
                        .map {
                            when {
                                it.error != null -> Error(it.error)
                                else -> PINChangedSuccess
                            }
                        }
                        .cast(LogInResult::class.java)
                        .onErrorReturn { Error(it.localizedMessage) }
                }
            }
        }
    }

    private val loginUserActionProcessor = ObservableTransformer<LoginUserAction, LogInResult> { action ->
        action.flatMap { data ->
            with(data.credentials) {
                val validationErrorMessage = validate()

                if (validationErrorMessage != null) {
                    Error(validationErrorMessage).asObservable()
                } else {
                    userRepository.loginUser(LoginData(identifier, password))
                        .subscribeOn(schedulersProvider.subscriptionScheduler())
                        .observeOn(schedulersProvider.observableScheduler())
                        .flatMap { credentials ->
                            when {
                                !credentials.error.isNullOrEmpty() -> Error(credentials.error).asObservable()
                                credentials.message?.token == null -> Error(context.getString(R.string.message_token_empty)).asObservable()
                                else -> {
                                    userRepository.saveAuthToken(credentials.message.token)
                                    userRepository.savePIN(password)
                                    userRepository.getAccountInfo()
                                        .subscribeOn(schedulersProvider.subscriptionScheduler())
                                        .observeOn(schedulersProvider.observableScheduler())
                                        .flatMap { info ->
                                            when {
                                                !info.error.isNullOrEmpty() -> Error(info.error).asObservable()
                                                info.message?.username == null -> Error(context.getString(R.string.sign_in_error_no_username)).asObservable()
                                                else -> {
                                                    registerRewardRepository.getRegisterReward()
                                                        .subscribeOn(schedulersProvider.subscriptionScheduler())
                                                        .observeOn(schedulersProvider.observableScheduler())
                                                        .map {
                                                            when {
                                                                !it.error.isNullOrEmpty() || it.message?.status == "claimed" -> {
                                                                    userRepository.apply {
                                                                        setUsername(info.message.username)
                                                                        savePrivateKey(info.message.privateKey)
                                                                        saveReferralCode(info.message.referralCode)
                                                                        setRewardClaimed()
                                                                        setUserRegistered()
                                                                        setUserVerified()
                                                                        userLoggedIn()
                                                                    }
                                                                    UserLoggedIn(userRepository.isRewardClaimed())
                                                                }
                                                                else -> {
                                                                    registerRewardRepository.saveReward(it.message)
                                                                    userRepository.apply {
                                                                        setUsername(info.message.username)
                                                                        savePrivateKey(info.message.privateKey)
                                                                        saveReferralCode(info.message.referralCode)
                                                                        setUserRegistered()
                                                                        setUserVerified()
                                                                        userLoggedIn()
                                                                    }
                                                                    UserLoggedIn(userRepository.isRewardClaimed())
                                                                }
                                                            }
                                                        }
                                                }
                                            }
                                        }
                                }
                            }
                        }.cast(LogInResult::class.java)
                        .startWith(InFlight)
                        .onErrorReturn { Error(it.localizedMessage) }
                }
            }
        }
    }

    private fun LoginData.validate(): String? {
        val errorRes = when {
            identifier.isNullOrBlank() -> R.string.validation_error_username_email_empty
            password.isBlank() -> R.string.validation_error_pin_empty
            password.length < requiredPinLength -> R.string.validation_error_pin_to_short
            else -> -1
        }

        if (errorRes != -1) {
            return context.getString(errorRes)
        }
        return null
    }

    companion object {
        private const val requiredPinLength = 6
    }
}