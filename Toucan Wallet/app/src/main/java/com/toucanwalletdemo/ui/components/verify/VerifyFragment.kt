package com.toucanwalletdemo.ui.components.verify

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.verify.mvi.VerifyAction
import com.toucanwalletdemo.ui.components.verify.mvi.VerifyResult
import com.toucanwalletdemo.utils.utils.showSoftInput
import kotlinx.android.synthetic.main.fragment_verify.*

class VerifyFragment: MviBaseFragment<VerifyAction, VerifyResult, VerifyViewState, VerifyViewModel>(
    VerifyViewModel::class.java
) {
    private val handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_verify, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pinInput.setUp(R.string.sign_up_verification_code)
        pinInput.onCodeCompletedListener = {
            viewModel.onVerify(pinInput.getText())
        }

        switchUserButton.setOnClickListener {
            viewModel.onSwitchUser()
        }

        showSoftInput(requireActivity())
    }

    override fun hasBottomBar() = false

    override fun onResume() {
        super.onResume()
        with(mainViewModel) {
            handler.postDelayed({ pinInput.getView(verifyCode).requestFocus() }, 500)
            verifyCode?.let {
                pinInput.setText(it)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
        mainViewModel.verifyCode = null
        if (pinInput.getText().isNotBlank()) mainViewModel.verifyCode = pinInput.getText()
    }

    override fun render(viewState: VerifyViewState) {
        with(viewState) {
            progressBar.isVisible = inProgress
            pinInput.isEnabled = !inProgress

            userVerified?.consume {
                navController.navigate(R.id.navigateToSignInFragment)
            }

            switchUser?.consume {
                navController.navigate(R.id.navigateToLogInFragment)
            }

            errorMessage?.consume {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}