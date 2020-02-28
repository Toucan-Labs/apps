package com.toucanpay.ui.components.account.mvi

import com.toucanpay.ui.base.mvi.MviAction

sealed class AccountAction: MviAction {

    object LoadUsernameAction: AccountAction()

    object LogOutUserAction: AccountAction()
}