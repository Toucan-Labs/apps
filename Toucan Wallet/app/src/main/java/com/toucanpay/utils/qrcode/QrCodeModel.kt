package com.toucanpay.utils.qrcode

import com.toucanpay.ui.model.QrCodeSignatureData

sealed class QrCode {
    object InvalidQrCode: QrCode()
    data class AccountQrCode(val username: String): QrCode()
    data class SignatureQrCode(val qrCodeSignatureData: QrCodeSignatureData): QrCode()
}