package com.toucanpay.ui.components.enterPINdialog

import android.os.Bundle
import android.os.Handler
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.toucanpay.R
import com.toucanpay.data.repositories.UserRepository
import com.toucanpay.ui.base.BaseDialogFragment
import com.toucanpay.utils.utils.forceShowSoftInput
import com.toucanpay.utils.utils.hideSoftInput
import kotlinx.android.synthetic.main.dialog_enter_pin.*
import org.koin.android.ext.android.inject

class EnterPINDialog: BaseDialogFragment() {

    private val userRepository: UserRepository by inject()
    private val handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_enter_pin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pinInput.setUp(R.string.sign_in_pin_label)

        pinInput.onCodeCompletedListener = {
            if (pinInput.getText() == userRepository.getPIN()) {
                hideSoftInput(requireContext(), view)
                mainViewModel.refreshQR.postValue(true)
                dismiss()
            } else {
                Toast.makeText(requireContext(), getString(R.string.authorize_invalid_pin), Toast.LENGTH_SHORT).show()
            }
        }

        pinInput.getDigit1View().setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                forceShowSoftInput(requireContext())
            }
        }

        dialog?.setCanceledOnTouchOutside(false)
        dialog?.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                navController.navigate(R.id.navigateToAccountFragment)
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed({ pinInput.getMainView().requestFocus() }, 50)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
        hideSoftInput(requireContext(), this.requireView())
    }
}