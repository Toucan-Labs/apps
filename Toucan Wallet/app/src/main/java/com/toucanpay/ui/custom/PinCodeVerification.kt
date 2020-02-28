package com.toucanpay.ui.custom

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.FrameLayout
import androidx.annotation.StringRes
import com.google.android.material.textfield.TextInputEditText
import com.toucanpay.R
import com.toucanpay.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.view_pin_code_verification.view.*

class PinCodeVerification @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    var nextView: View? = null
    var prevView: View? = null
    private val inputs: List<TextInputEditText>
    var onCodeCompletedListener: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.view_pin_code_verification, this)
        inputs = listOf(digit1, digit2, digit3, digit4)
        setInputsListeners()
    }

    override fun setEnabled(enabled: Boolean) {
        inputs.forEach { it.isEnabled = enabled }
        hintText.isEnabled = enabled
    }

    fun setUp(@StringRes hintRes: Int) {
        hintText.text = context.getString(hintRes)
    }

    fun getText() = "${digit1.text}${digit2.text}${digit3.text}${digit4.text}"

    fun setText(newText: String) {
        val split = newText.toCharArray()

        digit1.setText(split[0].toString())
        if (split.size > 1) digit2.setText(split[1].toString())
        if (split.size > 2) digit3.setText(split[2].toString())
        if (split.size > 3) digit4.setText(split[3].toString())
    }

    fun getView(newText: String?): View = when (newText?.length) {
        1 -> digit2
        2 -> digit3
        3 -> digit4
        else -> digit1
    }

    private fun setInputsListeners() {
        mainView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                digit1.requestFocus()
            }
        }

        inputs.forEachIndexed { index, textInputEditText ->
            textInputEditText.imeOptions = EditorInfo.IME_ACTION_NEXT
            textInputEditText.setSelectAllOnFocus(true)
            if (index < inputs.size) {
                textInputEditText.addTextChangedListener(object: SimpleTextWatcher() {

                    override fun afterTextChanged(s: Editable) {
                        val hasText = s.length == 1
                        if (hasText) {
                            inputs.getOrNull(index + 1)?.requestFocus()
                        }

                        if (index == inputs.size - 1 && hasText) {
                            nextView?.requestFocus()
                        }

                        if (inputs.size - 1 == index) {
                            onCodeCompletedListener?.invoke()
                        }
                    }
                })
                textInputEditText.setOnKeyListener { v, _, event ->
                    val textLength = (v as EditText).text.length
                    event?.let {
                        if (textLength == 0 && it.keyCode == KeyEvent.KEYCODE_DEL && it.action == KeyEvent.ACTION_DOWN) {
                            if (index == 0) {
                                prevView?.requestFocus()
                            } else {
                                inputs.getOrNull(index - 1)?.requestFocus()
                            }
                        }
                    }
                    false
                }

                textInputEditText.setOnFocusChangeListener { _, hasFocus ->
                    hintText.isSelected = hasFocus
                }
            }
        }
    }
}