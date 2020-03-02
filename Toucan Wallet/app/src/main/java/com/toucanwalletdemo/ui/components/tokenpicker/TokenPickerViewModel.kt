package com.toucanwalletdemo.ui.components.tokenpicker

import com.toucanwalletdemo.ui.base.mvi.MviViewModel
import com.toucanwalletdemo.ui.components.tokenpicker.mvi.TokenPickerAction
import com.toucanwalletdemo.ui.components.tokenpicker.mvi.TokenPickerActionProcessor
import com.toucanwalletdemo.ui.components.tokenpicker.mvi.TokenPickerResult

class TokenPickerViewModel: MviViewModel<TokenPickerAction, TokenPickerResult, TokenPickerViewState>(
    TokenPickerActionProcessor(), TokenPickerViewState.default()
) {

    override fun initialAction(): TokenPickerAction? = TokenPickerAction.GetTokensAction
}