package com.toucanwalletdemo.ui.components.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.signup.mvi.SignUpAction
import com.toucanwalletdemo.ui.components.signup.mvi.SignUpResult
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.view_sign_up_form.view.*

class SignUpFragment: MviBaseFragment<SignUpAction, SignUpResult, SignUpViewState, SignUpViewModel>(
    SignUpViewModel::class.java
) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_signup, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpForm.onFormConfirmedListener = {
            registerUser()
        }

        registerButton.setOnClickListener {
            registerUser()
        }

        signUpForm.emailInput.setActionType(EditorInfo.IME_ACTION_DONE) {
            registerUser()
        }

        loginButton.setOnClickListener {
            navController.navigate(R.id.navigateToLogInFragment)
        }
    }

    override fun hasBottomBar() = false

    override fun render(viewState: SignUpViewState) {
        with(viewState) {
            progressBar.isVisible = inProgress
            registerButton.isInvisible = inProgress
            loginButton.isEnabled = !inProgress
            signUpForm.isEnabled = !inProgress

            success?.consume {
                navController.navigate(R.id.navigateToVerifyDialog)
            }

            errorMessage?.consume {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun registerUser() {
        viewModel.onRegister(signUpForm.getUserRegistrationData())
    }
}