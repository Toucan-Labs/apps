package com.toucanpay.ui.components.authorize.mvi

import com.toucanpay.ui.base.mvi.MviAction

sealed class AuthorizeAction: MviAction {

    object LoadAccountInfoAction: AuthorizeAction()
}