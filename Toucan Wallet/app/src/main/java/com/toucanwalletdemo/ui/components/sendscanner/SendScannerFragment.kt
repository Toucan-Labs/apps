package com.toucanwalletdemo.ui.components.sendscanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.sendscanner.mvi.SendScannerAction
import com.toucanwalletdemo.ui.components.sendscanner.mvi.SendScannerResult
import com.toucanwalletdemo.ui.custom.scanner.QrCodeScannerFragment
import com.toucanwalletdemo.utils.qrcode.QrCode
import com.toucanwalletdemo.utils.qrcode.QrCodeManager
import com.toucanwalletdemo.utils.utils.vibrate
import org.koin.android.ext.android.inject

class SendScannerFragment: MviBaseFragment<SendScannerAction, SendScannerResult, SendScannerViewState, SendScannerViewModel>(
    SendScannerViewModel::class.java
) {

    private val args by navArgs<SendScannerFragmentArgs>()
    private val qrCodeManager: QrCodeManager by inject()
    lateinit var onQrCodeScanned: (String) -> Unit
    private var successScan: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_send_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onQrCodeScanned = {
            with(args.tradeInputData) {
                viewModel.onSendTokens(tokenSymbol, amount, it, reference)
            }
        }
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is QrCodeScannerFragment) {
            childFragment.qrCodeListener = this::handleScannedQrCode
        }
    }

    private fun handleScannedQrCode(text: String) {
        if (!successScan) {
            when (val qrCode = qrCodeManager.parse(text)) {
                is QrCode.AccountQrCode -> {
                    successScan = true
                    onQrCodeScanned.invoke(qrCode.username)
                }
                is QrCode.SignatureQrCode -> {
                    successScan = true
                    onQrCodeScanned.invoke(qrCode.qrCodeSignatureData.username)
                }
                else -> Toast.makeText(requireContext(), R.string.message_qr_code_not_recognized, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun render(viewState: SendScannerViewState) {
        with(viewState) {

            success?.consume {
                vibrate(requireActivity())
                mainViewModel.tradeTokensSuccess = true
                it?.let {
                    navController.navigate(SendScannerFragmentDirections.navigateToTradeTokensSuccess(it, true))
                }
            }

            error?.consume {
                it?.let {
                    navController.navigate(SendScannerFragmentDirections.navigateToTradeTokensError(it))
                }
            }
        }
    }

    override fun hasBottomBar() = false
}