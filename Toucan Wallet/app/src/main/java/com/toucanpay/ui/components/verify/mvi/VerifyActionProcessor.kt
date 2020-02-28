package com.toucanpay.ui.components.verify.mvi

import com.toucanpay.data.models.VerifyData
import com.toucanpay.data.repositories.UserRepository
import com.toucanpay.ui.base.mvi.*
import com.toucanpay.ui.components.verify.mvi.VerifyAction.VerifyUserAction
import com.toucanpay.ui.components.verify.mvi.VerifyResult.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class VerifyActionProcessor: MviActionsProcessor<VerifyAction, VerifyResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val userRepository: UserRepository by inject()

    override fun getActionProcessors(shared: Observable<VerifyAction>) = listOf(
        shared.connect(verifyUserActionProcessor),
        shared.connect(switchUserActionsProcessor)
    )

    private val verifyUserActionProcessor = ObservableTransformer<VerifyUserAction, VerifyResult> { action ->
        action.flatMap { data ->
            userRepository.verifyUser(VerifyData(userRepository.getUserEmail(), data.code))
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .observeOn(schedulersProvider.observableScheduler())
                .map {
                    if (it.error != null) {
                        Error(it.error)
                    } else {
                        userRepository.setUserVerified()
                        Success
                    }
                }
                .cast(VerifyResult::class.java)
                .startWith(InFlight)
                .onErrorReturn { Error(it.localizedMessage) }
        }
    }

    private val switchUserActionsProcessor = createActionProcessor<VerifyAction.SwitchUserAction, VerifyResult>(
        schedulersProvider,
        { InFlight },
        { Error(it.localizedMessage) }
    ) {
        userRepository.deleteUser()
        onNextSafe(MoveToLogInUser)
        onCompleteSafe()
    }
}