package com.toucanwalletdemo.ui.components.account

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.account.mvi.AccountAction
import com.toucanwalletdemo.ui.components.account.mvi.AccountActionProcessor
import com.toucanwalletdemo.ui.components.account.mvi.AccountResult

class AccountViewModel: MviViewModel<AccountAction, AccountResult, AccountViewState>(
    AccountActionProcessor(),
    AccountViewState.default()
) {

    override fun initialAction() = AccountAction.LoadUsernameAction

    fun onLogOutUser() = accept(AccountAction.LogOutUserAction)
}