package com.toucanwalletdemo.utils.qrcode

import com.toucanwalletdemo.ui.model.QrCodeSignatureData

sealed class QrCode {
    object InvalidQrCode: QrCode()
    data class AccountQrCode(val username: String): QrCode()
    data class SignatureQrCode(val qrCodeSignatureData: QrCodeSignatureData): QrCode()
}