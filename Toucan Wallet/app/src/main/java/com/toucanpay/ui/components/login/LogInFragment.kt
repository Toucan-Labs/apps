package com.toucanpay.ui.components.login

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.toucanpay.R
import com.toucanpay.ui.base.mvi.MviBaseFragment
import com.toucanpay.ui.components.login.mvi.LogInAction
import com.toucanpay.ui.components.login.mvi.LogInResult
import com.toucanpay.utils.utils.forceShowSoftInput
import com.toucanpay.utils.utils.hideSoftInput
import kotlinx.android.synthetic.main.fragment_login.*

class LogInFragment: MviBaseFragment<LogInAction, LogInResult, LogInViewState, LogInViewModel>(
    LogInViewModel::class.java
) {

    private val handler = Handler()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        usernameInput.setUp(R.string.sign_in_username_email)
        pinInput.setUp(R.string.sign_in_pin_label)

        pinInput.setActionType(EditorInfo.IME_ACTION_DONE) {
            loginUser()
        }

        pinInput.onCodeCompletedListener = {
            loginUser()
        }

        usernameInput.getMainView().setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                forceShowSoftInput(requireContext())
            }
        }

        loginButton.setOnClickListener {
            loginUser()
        }

        registerButton.setOnClickListener {
            navController.navigateUp()
        }

        forgotPINButton.setOnClickListener {
            viewModel.onPINReset(usernameInput.getText())
        }
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed({ usernameInput.getMainView().requestFocus() }, 500)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
        hideSoftInput(requireContext(), this.requireView())
    }

    private fun loginUser() {
        viewModel.onLogin(usernameInput.getText(), pinInput.getText())
    }

    override fun hasBottomBar() = false

    override fun render(viewState: LogInViewState) {
        with(viewState) {
            progressBar.isVisible = inProgress
            loginButton.isInvisible = inProgress
            usernameInput.isEnabled = !inProgress
            pinInput.isEnabled = !inProgress
            registerButton.isEnabled = !inProgress
            forgotPINButton.isEnabled = !inProgress

            userLoggedIn?.consume {
                it?.let {
                    when (it) {
                        true -> selectInitialTab()
                        else -> navController.navigate(R.id.navigateToClaimBonusFragment)
                    }
                }
            }

            changePIN?.consume {
                navController.navigate(R.id.navigateToForgotPinDialog)
            }

            errorMessage?.consume {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}