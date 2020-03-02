package com.toucanwalletdemo.ui.components.changepin.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.model.UserChangePinData
import kotlinx.android.synthetic.main.view_change_pin_form.view.*

class ChangePinForm @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    var onFormConfirmedListener: (() -> Unit)? = null
    private val inputs: List<View>

    init {
        LayoutInflater.from(context).inflate(R.layout.view_change_pin_form, this, true)
        inputs = listOf(emailPinInput, pinInput, pinRepeatedInput)

        orientation = VERTICAL

        emailPinInput.setUp(R.string.reset_pin_email_label)
        pinInput.setUp(R.string.reset_pin_pin_label)
        pinRepeatedInput.setUp(R.string.reset_pin_repeated_label)

        emailPinInput.setActionType(EditorInfo.IME_ACTION_NEXT) {
            pinInput.getMainView().requestFocus()
        }

        pinInput.setActionType(EditorInfo.IME_ACTION_NEXT) {
            pinRepeatedInput.getMainView().requestFocus()
        }

        pinRepeatedInput.setActionType(EditorInfo.IME_ACTION_DONE) {
            onFormConfirmedListener?.invoke()
        }

        emailPinInput.nextView = pinInput.getMainView()
        pinInput.prevView = emailPinInput.getMainView()
        pinInput.nextView = pinRepeatedInput.getMainView()
    }

    override fun setEnabled(enabled: Boolean) {
        inputs.forEach { it.isEnabled = enabled }
    }

    fun getChangePinData() = UserChangePinData(
        emailPinInput.getText(),
        pinInput.getText(),
        pinRepeatedInput.getText()
    )
}