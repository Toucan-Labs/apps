package com.toucanpay.ui.components.account.mvi

import android.content.Context
import com.toucanpay.R
import com.toucanpay.data.repositories.UserRepository
import com.toucanpay.ui.base.mvi.*
import com.toucanpay.ui.components.account.mvi.AccountAction.LoadUsernameAction
import com.toucanpay.ui.components.account.mvi.AccountAction.LogOutUserAction
import com.toucanpay.ui.components.account.mvi.AccountResult.*
import io.reactivex.Observable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class AccountActionProcessor: MviActionsProcessor<AccountAction, AccountResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val userRepository: UserRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<AccountAction>) = listOf(
        shared.connect(loadUserActionProcessor),
        shared.connect(logOutUserActionProcessor)
    )

    private val logOutUserActionProcessor = createActionProcessor<LogOutUserAction, AccountResult>(
        schedulersProvider,
        { InFlight },
        { Error(it.localizedMessage) }
    ) {
        userRepository.logoutUser()
        onNextSafe(LogOutSuccess)
        onCompleteSafe()
    }

    private val loadUserActionProcessor = createActionProcessor<LoadUsernameAction, AccountResult>(
        schedulersProvider,
        { InFlight },
        { Error(it.localizedMessage) }
    ) {
        val username = userRepository.getUsername()
        val referralCode = userRepository.getReferralCode()

        if (username != null) {
            onNextSafe(Success(username, referralCode))
        } else {
            onNextSafe(Error(context.getString(R.string.authorize_qr_code_error)))
        }
        onCompleteSafe()
    }
}