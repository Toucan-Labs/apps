package com.toucanwalletdemo.ui.components.authorize

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.authorize.mvi.AuthorizeAction
import com.toucanwalletdemo.ui.components.authorize.mvi.AuthorizeActionProcessor
import com.toucanwalletdemo.ui.components.authorize.mvi.AuthorizeResult

class AuthorizeViewModel: MviViewModel<AuthorizeAction, AuthorizeResult, AuthorizeViewState>(
    AuthorizeActionProcessor(),
    AuthorizeViewState.default()
) {

    override fun initialAction(): AuthorizeAction? = null

    fun onAccountInfo() = accept(AuthorizeAction.LoadAccountInfoAction)
}