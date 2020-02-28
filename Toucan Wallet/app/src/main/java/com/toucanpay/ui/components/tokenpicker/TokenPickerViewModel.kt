package com.toucanpay.ui.components.tokenpicker

import com.toucanpay.ui.base.mvi.MviViewModel
import com.toucanpay.ui.components.tokenpicker.mvi.TokenPickerAction
import com.toucanpay.ui.components.tokenpicker.mvi.TokenPickerActionProcessor
import com.toucanpay.ui.components.tokenpicker.mvi.TokenPickerResult

class TokenPickerViewModel: MviViewModel<TokenPickerAction, TokenPickerResult, TokenPickerViewState>(
    TokenPickerActionProcessor(), TokenPickerViewState.default()
) {

    override fun initialAction(): TokenPickerAction? = TokenPickerAction.GetTokensAction
}