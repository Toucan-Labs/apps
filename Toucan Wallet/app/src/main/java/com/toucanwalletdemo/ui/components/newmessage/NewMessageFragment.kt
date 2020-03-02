package com.toucanwalletdemo.ui.components.newmessage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.newmessage.mvi.NewMessageAction
import com.toucanwalletdemo.ui.components.newmessage.mvi.NewMessageResult
import com.toucanwalletdemo.ui.custom.scanner.QrCodeScannerFragment
import com.toucanwalletdemo.utils.qrcode.QrCode
import com.toucanwalletdemo.utils.qrcode.QrCodeManager
import com.toucanwalletdemo.utils.utils.hideSoftInput
import kotlinx.android.synthetic.main.fragment_new_message.*
import org.koin.android.ext.android.inject

class NewMessageFragment: MviBaseFragment<NewMessageAction, NewMessageResult, NewMessageViewState, NewMessageViewModel>(
    NewMessageViewModel::class.java
) {

    private val qrCodeManager: QrCodeManager by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_new_message, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        closeButton.setOnClickListener {
            hideSoftInput(requireContext(), view)
            navController.navigateUp()
        }

        sendMessageButton.setOnClickListener {
            viewModel.onMessageSendButton(
                usernameInput.text.toString()
            )
        }
    }

    override fun onAttachFragment(childFragment: Fragment) {
        super.onAttachFragment(childFragment)
        if (childFragment is QrCodeScannerFragment) {
            childFragment.qrCodeListener = ::handleScannedQrCode
        }
    }

    private fun handleScannedQrCode(text: String) {
        val qrCode = qrCodeManager.parse(text)
        if (qrCode is QrCode.AccountQrCode) {
            navController.navigate(NewMessageFragmentDirections.navigateToMessagesThreadFragment(qrCode.username))
        } else {
            Toast.makeText(requireContext(), R.string.message_qr_code_not_recognized, Toast.LENGTH_SHORT).show()
        }
    }

    override fun render(viewState: NewMessageViewState) {
        with(viewState) {
            progressBar.isVisible = inProgress
            sendMessageButton.isInvisible = inProgress
            usernameInput.isEnabled = !inProgress

            moveToThread?.consume {
                it?.let {
                    navController.navigate(NewMessageFragmentDirections.navigateToMessagesThreadFragment(it))
                }
            }

            error?.consume {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun hasBottomBar(): Boolean = false
}