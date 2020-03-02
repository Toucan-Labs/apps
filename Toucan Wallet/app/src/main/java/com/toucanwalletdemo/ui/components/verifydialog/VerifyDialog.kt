package com.toucanwalletdemo.ui.components.verifydialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.BaseBottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_reset_pin.*

class VerifyDialog: BaseBottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.dialog_verify, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        navController.navigate(R.id.navigateToVerifyFragment)
    }
}