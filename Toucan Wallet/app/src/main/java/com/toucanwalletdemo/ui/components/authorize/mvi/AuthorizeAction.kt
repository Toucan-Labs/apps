package com.toucanwalletdemo.ui.components.authorize.mvi

import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class AuthorizeAction: MviAction {

    object LoadAccountInfoAction: AuthorizeAction()
}