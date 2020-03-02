package com.toucanwalletdemo.ui.custom

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
import com.toucanwalletdemo.R
import com.toucanwalletdemo.utils.SimpleTextWatcher
import kotlinx.android.synthetic.main.view_pin_code_input.view.*

class PINCodeInput @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    var nextView: View? = null
    var prevView: View? = null
    private val inputs: List<TextInputEditText>
    var onCodeCompletedListener: (() -> Unit)? = null

    init {
        View.inflate(context, R.layout.view_pin_code_input, this)
        inputs = listOf(digit1, digit2, digit3, digit4, digit5, digit6)
        setInputsListeners()
    }

    override fun setEnabled(enabled: Boolean) {
        inputs.forEach { it.isEnabled = enabled }
        hintText.isEnabled = enabled
    }

    fun setUp(@StringRes hintRes: Int) {
        hintText.text = context.getString(hintRes)
    }

    fun setPIN(d1: String, d2: String, d3: String, d4: String, d5: String, d6: String) {
        digit1.setText(d1)
        digit2.setText(d2)
        digit3.setText(d3)
        digit4.setText(d4)
        digit5.setText(d5)
        digit6.setText(d6)
    }

    fun setActionType(imeAction: Int, imeActionListener: () -> Unit) {
        digit6.imeOptions = imeAction
        digit6.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == imeAction) {
                imeActionListener()
            }
            true
        }
    }

    fun getMainView(): View = mainView

    fun getDigit1View(): View = digit1

    fun getText() =
        "${digit1.text}${digit2.text}${digit3.text}${digit4.text}${digit5.text}${digit6.text}"

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