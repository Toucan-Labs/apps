package com.toucanwalletdemo.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.toucanwalletdemo.R
import kotlinx.android.synthetic.main.view_form_input.view.*

class FormInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.view_form_input, this, true)
    }

    override fun setEnabled(enabled: Boolean) {
        inputLayout.isEnabled = enabled
    }

    fun setActionType(imeAction: Int, imeActionListener: () -> Unit) {
        inputText.imeOptions = imeAction
        inputText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == imeAction) {
                imeActionListener()
            }
            true
        }
    }

    fun setUp(@StringRes hintRes: Int) {
        inputLayout.hint = context.getString(hintRes)
    }

    fun getMainView(): View = inputText

    fun setInputType(type: Int) {
        inputText.inputType = type
    }

    fun getText(): String = inputText.text?.toString() ?: ""
}