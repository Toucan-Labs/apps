package com.toucanpay.ui.components.signin.mvi

import android.content.Context
import com.toucanpay.R
import com.toucanpay.data.models.LoginData
import com.toucanpay.data.models.ResetPinData
import com.toucanpay.data.repositories.RegisterRewardRepository
import com.toucanpay.data.repositories.UserRepository
import com.toucanpay.ui.base.mvi.*
import com.toucanpay.ui.components.signin.mvi.SignInAction.*
import com.toucanpay.ui.components.signin.mvi.SignInResult.*
import com.toucanpay.utils.extensions.asObservable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SignInActionProcessor: MviActionsProcessor<SignInAction, SignInResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val registerRewardRepository: RegisterRewardRepository by inject()
    private val userRepository: UserRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<SignInAction>) = listOf(
        shared.connect(loadUserActionProcessor),
        shared.connect(resetPinActionsProcessor),
        shared.connect(switchUserActionsProcessor),
        shared.connect(loginUserActionProcessor)
    )

    private val loadUserActionProcessor = createActionProcessor<LoadUserAction, SignInResult>(
        schedulersProvider,
        { InFlight },
        { Error(it.localizedMessage) }
    ) {
        onNextSafe(UserLoaded(userRepository.getUsername()))
        onCompleteSafe()
    }

    private val switchUserActionsProcessor = createActionProcessor<SwitchUserAction, SignInResult>(
        schedulersProvider,
        { InFlight },
        { Error(it.localizedMessage) }
    ) {
        userRepository.deleteUser()
        onNextSafe(MoveToLogInUser)
        onCompleteSafe()
    }

    private val resetPinActionsProcessor = ObservableTransformer<ResetPinAction, SignInResult> { action ->
        action.flatMap {
            userRepository.resetPin(ResetPinData(userRepository.getIdentifier()))
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .observeOn(schedulersProvider.observableScheduler())
                .map {
                    when {
                        it.error != null -> Error(it.error)
                        else -> {
                            userRepository.userLoggedIn()
                            MoveToResetPin
                        }
                    }
                }
                .cast(SignInResult::class.java)
                .onErrorReturn { Error(it.localizedMessage) }
        }
    }

    private val loginUserActionProcessor = ObservableTransformer<LoginUserAction, SignInResult> { action ->
        action.flatMap { data ->
            val username = userRepository.getUsername()
            val privateKey = userRepository.getPrivateKey()

            userRepository.loginUser(LoginData(userRepository.getIdentifier(), data.password))
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .observeOn(schedulersProvider.observableScheduler())
                .flatMap { credentials ->
                    if (username != null && privateKey != null) {
                        when {
                            !credentials.error.isNullOrEmpty() -> Error(credentials.error).asObservable()
                            credentials.message?.token == null -> Error(context.getString(R.string.message_token_empty)).asObservable()
                            else -> {
                                userRepository.apply {
                                    saveAuthToken(credentials.message.token)
                                    savePIN(data.password)
                                    userLoggedIn()
                                }
                                UserLoggedIn(userRepository.isRewardClaimed()).asObservable()
                            }
                        }
                    } else {
                        userRepository.apply {
                            saveAuthToken(credentials.message?.token!!)
                            savePIN(data.password)
                        }
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
                }.cast(SignInResult::class.java)
                .startWith(InFlight)
                .onErrorReturn { Error(it.localizedMessage) }
        }
    }
}