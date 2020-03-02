package com.toucanwalletdemo.ui.components.account.mvi

import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class AccountAction: MviAction {

    object LoadUsernameAction: AccountAction()

    object LogOutUserAction: AccountAction()
}