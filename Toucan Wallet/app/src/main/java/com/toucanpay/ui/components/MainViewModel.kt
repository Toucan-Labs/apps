package com.toucanpay.ui.components

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * This view model can be shared across all fragments and should be used to pass data on fragment back navigation,
 * for example in scanner
 */
class MainViewModel: ViewModel() {

    var tradeTokensSuccess: Boolean = false
    var token: String = ""
    var refreshQR = MutableLiveData<Boolean>()

    var verifyCode: String? = null
}