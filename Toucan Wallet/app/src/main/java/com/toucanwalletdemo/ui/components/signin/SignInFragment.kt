package com.toucanwalletdemo.ui.components.signin

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.signin.mvi.SignInAction
import com.toucanwalletdemo.ui.components.signin.mvi.SignInResult
import com.toucanwalletdemo.utils.qrcode.QrCode
import com.toucanwalletdemo.utils.qrcode.QrCodeManager
import com.toucanwalletdemo.utils.utils.getTimeOfDay
import com.toucanwalletdemo.utils.utils.showSoftInput
import kotlinx.android.synthetic.main.fragment_signin.*
import org.koin.android.ext.android.inject
import org.koin.standalone.KoinComponent

class SignInFragment: MviBaseFragment<SignInAction, SignInResult, SignInViewState, SignInViewModel>(
    SignInViewModel::class.java
), KoinComponent {

    private val handler = Handler()
    private val qrCodeManager: QrCodeManager by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_signin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pinInput.setUp(R.string.sign_in_pin_label)
        pinInput.onCodeCompletedListener = {
            viewModel.onLogin(pinInput.getText())
        }

        showSoftInput(requireActivity())

        resetPINButton.setOnClickListener {
            viewModel.onResetPin()
        }

        switchUserButton.setOnClickListener {
            viewModel.onSwitchUser()
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed({ pinInput.getMainView().requestFocus() }, 500)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
    }

    override fun hasBottomBar() = false

    override fun render(viewState: SignInViewState) {
        with(viewState) {
            progressBar.isVisible = inProgress
            pinInput.isEnabled = !inProgress
            resetPINButton.isEnabled = !inProgress
            switchUserButton.isEnabled = !inProgress

            titleText.isVisible = !username.isNullOrBlank()
            titleText.text = getString(R.string.sign_in_title, getTimeOfDay(), username)

            username?.let {
                qrCodeView.setImageBitmap(qrCodeManager.generate(QrCode.AccountQrCode(it)))
            }

            userLoggedIn?.consume {
                it?.let {
                    when (it) {
                        true -> selectInitialTab()
                        else -> navController.navigate(R.id.navigateToClaimBonusFragment)
                    }
                }
            }

            resetPin?.consume {
                navController.navigate(R.id.navigateToResetPinDialog)
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