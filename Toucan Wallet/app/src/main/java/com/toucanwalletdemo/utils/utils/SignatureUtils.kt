package com.toucanwalletdemo.utils.utils

import com.toucanwalletdemo.ui.model.SignedSignatureData
import org.bouncycastle.util.encoders.Hex
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Sign
import java.math.BigInteger

fun getSignatureData(privateKey: String): SignedSignatureData {

    val msg = String.format("%032d", System.currentTimeMillis())
    val signature = Sign.signMessage(msg.toByteArray(), ECKeyPair.create(BigInteger(privateKey, 16)), false)

    return SignedSignatureData(Hex.toHexString(signature.r + signature.s), msg)
}