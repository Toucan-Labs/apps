package com.toucanwalletdemo.ui.components.tokenpicker.mvi

import com.toucanwalletdemo.ui.base.mvi.MviAction

sealed class TokenPickerAction: MviAction {

    object GetTokensAction: TokenPickerAction()
}