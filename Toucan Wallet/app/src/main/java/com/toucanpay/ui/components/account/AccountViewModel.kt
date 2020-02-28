package com.toucanpay.ui.components.account

import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.account.mvi.AccountAction
import com.toucanpay.ui.components.account.mvi.AccountActionProcessor
import com.toucanpay.ui.components.account.mvi.AccountResult

class AccountViewModel: MviViewModel<AccountAction, AccountResult, AccountViewState>(
    AccountActionProcessor(),
    AccountViewState.default()
) {

    override fun initialAction() = AccountAction.LoadUsernameAction

    fun onLogOutUser() = accept(AccountAction.LogOutUserAction)
}