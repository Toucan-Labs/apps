package com.toucanwalletdemo.ui.components.requestscanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.requestscanner.mvi.RequestScannerAction
import com.toucanwalletdemo.ui.components.requestscanner.mvi.RequestScannerResult
import com.toucanwalletdemo.ui.custom.scanner.QrCodeScannerFragment
import com.toucanwalletdemo.ui.model.QrCodeSignatureData
import com.toucanwalletdemo.utils.qrcode.QrCode
import com.toucanwalletdemo.utils.qrcode.QrCodeManager
import com.toucanwalletdemo.utils.utils.vibrate
import org.koin.android.ext.android.inject
import org.koin.standalone.KoinComponent

class RequestScannerFragment: MviBaseFragment<RequestScannerAction, RequestScannerResult, RequestScannerViewState, RequestScannerViewModel>(
    RequestScannerViewModel::class.java
), KoinComponent {

    private val args by navArgs<RequestScannerFragmentArgs>()
    private val qrCodeManager: QrCodeManager by inject()
    private lateinit var onQrCodeScanned: (QrCodeSignatureData) -> Unit
    private var successScan: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_request_scanner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onQrCodeScanned = {
            with(args.tradeInputData) {
                viewModel.onRequestTokens(it.signature, it.random, it.username, this.amount, this.tokenSymbol, this.reference)
            }
        }
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is QrCodeScannerFragment) {
            childFragment.qrCodeListener = this::handleScannerQrCode
        }
    }

    private fun handleScannerQrCode(text: String) {
        if (!successScan) {
            val qrCode = qrCodeManager.parse(text)
            if (qrCode is QrCode.SignatureQrCode) {
                successScan = true
                onQrCodeScanned.invoke(qrCode.qrCodeSignatureData)
            } else {
                Toast.makeText(requireContext(), R.string.message_qr_code_not_recognized, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun render(viewState: RequestScannerViewState) {
        with(viewState) {

            success?.consume {
                vibrate(requireActivity())
                mainViewModel.tradeTokensSuccess = true
                it?.let {
                    navController.navigate(RequestScannerFragmentDirections.navigateToTradeTokensSuccess(it, false))
                }
            }

            error?.consume {
                it?.let {
                    navController.navigate(RequestScannerFragmentDirections.navigateToTradeTokensError(it))
                }
            }
        }
    }

    override fun hasBottomBar() = false
}