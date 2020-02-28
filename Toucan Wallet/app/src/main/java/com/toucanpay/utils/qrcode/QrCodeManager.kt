package com.toucanpay.utils.qrcode

import android.graphics.Bitmap
import com.google.zxing.EncodeHintType
import com.toucanpay.ui.model.QrCodeSignatureData
import com.toucanpay.utils.qrcode.QrCode.*
import net.glxn.qrgen.android.QRCode

class QrCodeManager {

    fun generate(qrCode: QrCode): Bitmap {
        val text = when (qrCode) {
            is InvalidQrCode -> ""
            is AccountQrCode -> {
                PREFIX_ACCOUNT +
                        "$SEPARATOR${qrCode.username}"
            }
            is SignatureQrCode -> with(qrCode) {
                PREFIX_SIGNATURE +
                        "$SEPARATOR${qrCodeSignatureData.username}" +
                        "$SEPARATOR${qrCodeSignatureData.signature}" +
                        "$SEPARATOR${qrCodeSignatureData.random}"
            }
        }
        return createQrCode(text)
    }

    fun parse(text: String): QrCode {
        val parts = text.split(SEPARATOR).map { it.trim() }
        if (parts.isEmpty()) {
            return InvalidQrCode
        }

        return try {
            when (parts[0]) {
                PREFIX_ACCOUNT -> if (parts.size == 2) AccountQrCode(parts[1]) else InvalidQrCode
                PREFIX_SIGNATURE -> if (parts.size == 4) {
                    val data = QrCodeSignatureData(parts[1], parts[2], parts[3])
                    SignatureQrCode(data)
                } else {
                    InvalidQrCode
                }
                else -> InvalidQrCode
            }
        } catch (t: Throwable) {
            InvalidQrCode
        }
    }

    private fun createQrCode(text: String) =
        QRCode.from(text)
            .withSize(500, 500)
            .withHint(EncodeHintType.MARGIN, 0)
            .bitmap()

    companion object {
        private const val SEPARATOR = "#"
        private const val PREFIX_ACCOUNT = "ACCOUNT_QR"
        private const val PREFIX_SIGNATURE = "SIGNATURE_QR"
    }
}