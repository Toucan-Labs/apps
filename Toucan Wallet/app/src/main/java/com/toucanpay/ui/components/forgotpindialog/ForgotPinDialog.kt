package com.toucanpay.ui.components.forgotpindialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toucanpay.R
import com.toucanpay.ui.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_reset_pin.*

class ForgotPinDialog: BaseBottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_reset_pin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener {
            dismiss()
        }
    }
}