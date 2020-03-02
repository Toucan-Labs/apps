package com.toucanwalletdemo.ui.components.authorize.mvi

import android.content.Context
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.repositories.UserRepository
import com.toucanwalletdemo.ui.base.mvi.*
import com.toucanwalletdemo.ui.components.authorize.mvi.AuthorizeResult.*
import com.toucanwalletdemo.ui.model.QrCodeSignatureData
import com.toucanwalletdemo.utils.utils.getSignatureData
import io.reactivex.Observable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class AuthorizeActionProcessor: MviActionsProcessor<AuthorizeAction, AuthorizeResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val userRepository: UserRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<AuthorizeAction>) = listOf(
        shared.connect(generateQrCodeActionProcessor)
    )

    private val generateQrCodeActionProcessor = createActionProcessor<AuthorizeAction.LoadAccountInfoAction, AuthorizeResult>(
        schedulersProvider,
        { InFlight },
        { Error(it.localizedMessage) }
    ) {
        val privateKey = userRepository.getPrivateKey()
        val username = userRepository.getUsername()

        if (privateKey != null && username != null) {
            val signatureData = getSignatureData(privateKey)
            onNextSafe(Success(QrCodeSignatureData(username, signatureData.signature, signatureData.random)))
        } else {
            onNextSafe(Error(context.getString(R.string.authorize_qr_code_error)))
        }
        onCompleteSafe()
    }
}