package com.toucanwalletdemo.ui.components.signup.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.model.UserRegistrationData
import kotlinx.android.synthetic.main.view_sign_up_form.view.*

class SignUpForm @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): LinearLayout(context, attrs, defStyleAttr) {

    var onFormConfirmedListener: (() -> Unit)? = null
    private val inputs: List<View>

    init {
        LayoutInflater.from(context).inflate(R.layout.view_sign_up_form, this, true)
        inputs = listOf(usernameInput, pinInput, pinRepeatedInput, emailInput)

        orientation = VERTICAL

        usernameInput.setUp(R.string.sign_up_username_label)
        pinInput.setUp(R.string.sign_up_pin_label)
        pinRepeatedInput.setUp(R.string.sign_up_pin_repeated_label)
        referralCodeInput.setUp(R.string.sign_up_referral_code)
        emailInput.setUp(R.string.sign_up_email_label)

        pinInput.setActionType(EditorInfo.IME_ACTION_NEXT) {
            pinRepeatedInput.getMainView().requestFocus()
        }

        pinRepeatedInput.setActionType(EditorInfo.IME_ACTION_NEXT) {
            referralCodeInput.getMainView().requestFocus()
        }

        pinInput.nextView = pinRepeatedInput.getMainView()
        pinRepeatedInput.prevView = pinInput.getMainView()
        pinRepeatedInput.nextView = referralCodeInput.getMainView()
        emailInput.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS)
    }

    override fun setEnabled(enabled: Boolean) {
        inputs.forEach { it.isEnabled = enabled }
    }

    fun getUserRegistrationData() = UserRegistrationData(
        usernameInput.getText(),
        pinInput.getText(),
        pinRepeatedInput.getText(),
        referralCodeInput.getText(),
        emailInput.getText()
    )
}