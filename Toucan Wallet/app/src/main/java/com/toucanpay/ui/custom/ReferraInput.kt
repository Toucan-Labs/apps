package com.toucanpay.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.toucanpay.R
import kotlinx.android.synthetic.main.view_form_input.view.*

class ReferralCodeInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_referral_code_input, this, true)
    }

    override fun setEnabled(enabled: Boolean) {
        inputLayout.isEnabled = enabled
    }

    fun setUp(@StringRes hintRes: Int) {
        inputLayout.hint = context.getString(hintRes)
    }

    fun getMainView(): View = inputText

    fun getText(): String = inputText.text?.toString() ?: ""
}