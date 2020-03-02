package com.toucanwalletdemo.ui.components.scanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.BaseFragment
import com.toucanwalletdemo.ui.custom.scanner.QrCodeScannerFragment
import com.toucanwalletdemo.utils.qrcode.QrCode
import com.toucanwalletdemo.utils.qrcode.QrCodeManager
import org.koin.android.ext.android.inject

class ScannerFragment: BaseFragment() {

    private val qrCodeManager: QrCodeManager by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_scanner, container, false)

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is QrCodeScannerFragment) {
            childFragment.qrCodeListener = this::handleScannedQrCode
        }
    }

    override fun drawBelowBottomBar() = true

    private fun handleScannedQrCode(text: String) {
        val qrCode = qrCodeManager.parse(text)
        if (qrCode is QrCode.AccountQrCode) {
            Toast.makeText(requireContext(), qrCode.username, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), R.string.message_qr_code_not_recognized, Toast.LENGTH_SHORT).show()
        }
    }
}