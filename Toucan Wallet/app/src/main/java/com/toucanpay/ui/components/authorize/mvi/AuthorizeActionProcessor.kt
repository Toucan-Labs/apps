package com.toucanpay.ui.components.authorize.mvi

import android.content.Context
import com.toucanpay.R
import com.toucanpay.data.repositories.UserRepository
import com.toucanpay.ui.base.mvi.*
import com.toucanpay.ui.components.authorize.mvi.AuthorizeResult.*
import com.toucanpay.ui.model.QrCodeSignatureData
import com.toucanpay.utils.utils.getSignatureData
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