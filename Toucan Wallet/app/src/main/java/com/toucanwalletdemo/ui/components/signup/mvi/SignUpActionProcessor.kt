package com.toucanwalletdemo.ui.components.signup.mvi

import android.content.Context
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.RegisterData
import com.toucanwalletdemo.data.repositories.UserRepository
import com.toucanwalletdemo.ui.base.mvi.MviActionsProcessor
import com.toucanwalletdemo.ui.base.mvi.SchedulersProvider
import com.toucanwalletdemo.ui.components.signup.mvi.SignUpAction.RegisterAction
import com.toucanwalletdemo.ui.components.signup.mvi.SignUpResult.*
import com.toucanwalletdemo.ui.model.UserRegistrationData
import com.toucanwalletdemo.utils.extensions.asObservable
import com.toucanwalletdemo.utils.utils.emailRegex
import com.toucanwalletdemo.utils.utils.usernameLengthRegex
import com.toucanwalletdemo.utils.utils.usernameRegex
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SignUpActionProcessor: MviActionsProcessor<SignUpAction, SignUpResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val userRepository: UserRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<SignUpAction>) = listOf(
        shared.connect(registerActionProcessor)
    )

    private val registerActionProcessor = ObservableTransformer<RegisterAction, SignUpResult> { action ->
        action.flatMap { it ->
            with(it.data) {
                val validationErrorMessage = validate()
                if (validationErrorMessage != null) {
                    Error(validationErrorMessage).asObservable()
                } else {
                    userRepository.registerUser(RegisterData(email, pin, username, referCode))
                        .observeOn(schedulersProvider.observableScheduler())
                        .subscribeOn(schedulersProvider.subscriptionScheduler())
                        .map {
                            if (it.error != null) {
                                Error(it.error)
                            } else {
                                userRepository.setUserRegistered()
                                userRepository.createUser(username, email, pin)
                                Success
                            }
                        }
                        .cast(SignUpResult::class.java)
                        .startWith(InFlight)
                        .onErrorReturn { Error(it.localizedMessage) }
                }
            }
        }
    }

    private fun UserRegistrationData.validate(): String? {
        val errorRes = when {
            username.isBlank() -> R.string.validation_error_username_empty
            !usernameRegex().matches(username) -> R.string.validation_error_username
            !usernameLengthRegex().matches(username) -> R.string.validation_error_username_length
            pin.isBlank() -> R.string.validation_error_pin_empty
            pin.length < requiredPinLength -> R.string.validation_error_pin_to_short
            pinRepeated.isBlank() -> R.string.validation_error_pin_repeated_empty
            pin != pinRepeated -> R.string.validation_error_pin_repeated_does_not_match
            email.isBlank() -> R.string.validation_error_email_empty
            !referCode.isBlank() && referCode.length < 6 -> R.string.validation_refer_code
            !emailRegex().matches(email) -> R.string.validation_error_email_empty
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