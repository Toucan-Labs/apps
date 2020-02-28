package com.toucanpay.ui.components.changepin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.toucanpay.R
import com.toucanpay.ui.base.mvi.MviBaseFragment
import com.toucanpay.ui.components.changepin.mvi.ChangePinAction
import com.toucanpay.ui.components.changepin.mvi.ChangePinResult
import kotlinx.android.synthetic.main.fragment_change_pin.*

class ChangePinFragment: MviBaseFragment<ChangePinAction, ChangePinResult, ChangePinViewState, ChangePinViewModel>(
    ChangePinViewModel::class.java
) {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_change_pin, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changePinForm.onFormConfirmedListener = {
            changePin()
        }

        changePinButton.setOnClickListener {
            changePin()
        }

        backButton.setOnClickListener {
            navController.navigateUp()
        }
    }

    override fun render(viewState: ChangePinViewState) {
        with(viewState) {
            progressBar.isVisible = inProgress
            changePinButton.isInvisible = inProgress
            changePinForm.isEnabled = !inProgress

            success?.consume {
                navController.navigate(R.id.navigateToSignInFragment)
                Toast.makeText(requireContext(), getString(R.string.reset_pin_success), Toast.LENGTH_SHORT).show()
            }

            errorMessage?.consume {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun hasBottomBar() = false

    private fun changePin() {
        viewModel.onChangePin(changePinForm.getChangePinData())
    }
}