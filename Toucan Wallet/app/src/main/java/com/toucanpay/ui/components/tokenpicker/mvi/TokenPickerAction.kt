package com.toucanpay.ui.components.tokenpicker.mvi

import com.toucanpay.ui.base.mvi.MviAction

sealed class TokenPickerAction: MviAction {

    object GetTokensAction: TokenPickerAction()
}