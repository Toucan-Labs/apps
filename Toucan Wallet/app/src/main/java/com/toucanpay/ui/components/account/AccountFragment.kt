package com.toucanpay.ui.components.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.toucanpay.R
import com.toucanpay.ui.base.mvi.MviBaseFragment
import com.toucanpay.ui.components.account.mvi.AccountAction
import com.toucanpay.ui.components.account.mvi.AccountResult
import com.toucanpay.utils.extensions.navigateIfNotIn
import com.toucanpay.utils.qrcode.QrCode
import com.toucanpay.utils.qrcode.QrCodeManager
import kotlinx.android.synthetic.main.fragment_account.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class AccountFragment: MviBaseFragment<AccountAction, AccountResult, AccountViewState, AccountViewModel>(
    AccountViewModel::class.java
), KoinComponent {

    private val qrCodeManager: QrCodeManager by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_account, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        logoutButton.setOnClickListener {
            viewModel.onLogOutUser()
        }

        referButton.setOnClickListener {
            navController.navigate(R.id.navigateToAuthorizeFragment)
        }
    }

    override fun render(viewState: AccountViewState) {
        with(viewState) {

            username?.let {
                usernameText.text = it
                qrCodeView.setImageBitmap(qrCodeManager.generate(QrCode.AccountQrCode(it)))
            }

            referralCode?.let {
                referralCodeText.text = it
            }

            logOutSuccess?.consume {
                selectInitialTab()
                navController.navigateIfNotIn(R.id.showSignInFragment)
            }

            error?.consume {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}