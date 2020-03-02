package com.toucanwalletdemo.ui.components.changepin.mvi

import android.content.Context
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.ChangePinData
import com.toucanwalletdemo.data.models.LoginData
import com.toucanwalletdemo.data.repositories.UserRepository
import com.toucanwalletdemo.ui.base.mvi.MviActionsProcessor
import com.toucanwalletdemo.ui.base.mvi.SchedulersProvider
import com.toucanwalletdemo.ui.components.changepin.mvi.ChangePinAction.ChangeUserPinAction
import com.toucanwalletdemo.ui.components.changepin.mvi.ChangePinResult.*
import com.toucanwalletdemo.ui.model.UserChangePinData
import com.toucanwalletdemo.utils.extensions.asObservable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ChangePinActionProcessor: MviActionsProcessor<ChangePinAction, ChangePinResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val userRepository: UserRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<ChangePinAction>) = listOf(
        shared.connect(changePinActionProcessor)
    )

    private val changePinActionProcessor = ObservableTransformer<ChangeUserPinAction, ChangePinResult> { action ->
        action.flatMap { item ->
            with(item.data) {
                val validationErrorMessage = validate()
                if (validationErrorMessage != null) {
                    Error(validationErrorMessage).asObservable()
                } else {
                    userRepository.loginUser(LoginData(userRepository.getIdentifier(), emailPin))
                        .observeOn(schedulersProvider.observableScheduler())
                        .subscribeOn(schedulersProvider.subscriptionScheduler())
                        .map {
                            when {
                                it.error != null -> Error(it.error)
                                it.message?.token == null -> Error(context.getString(R.string.reset_pin_error))
                                else -> {
                                    userRepository.saveAuthToken(it.message.token)
                                }
                            }
                        }
                        .flatMap {
                            userRepository.changePin(ChangePinData(emailPin, pin))
                                .observeOn(schedulersProvider.observableScheduler())
                                .subscribeOn(schedulersProvider.subscriptionScheduler())
                        }
                        .map {
                            if (it.error != null) {
                                Error(it.error)
                            } else {
                                userRepository.logoutUser()
                                Success
                            }
                        }
                        .cast(ChangePinResult::class.java)
                        .startWith(InFlight)
                        .onErrorReturn { Error(it.localizedMessage) }
                }
            }
        }
    }

    private fun UserChangePinData.validate(): String? {
        val errorRes = when {
            emailPin.isBlank() -> R.string.validation_error_old_pin_empty
            pin.isBlank() -> R.string.validation_error_pin_empty
            pin.length < requiredPinLength -> R.string.validation_error_pin_to_short
            pinRepeated.isBlank() -> R.string.validation_error_pin_repeated_empty
            pin != pinRepeated -> R.string.validation_error_pin_repeated_does_not_match
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