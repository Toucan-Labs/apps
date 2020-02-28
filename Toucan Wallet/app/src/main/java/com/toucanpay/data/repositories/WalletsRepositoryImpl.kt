package com.toucanpay.data.repositories

import com.toucanpay.data.models.RequestTokenData
import com.toucanpay.data.models.SendTokenData
import com.toucanpay.data.remote.repositories.WalletsRemoteRepository

class WalletsRepositoryImpl(
    private val walletsRemoteRepository: WalletsRemoteRepository
): WalletsRepository {

    override fun getWalletBalances() = walletsRemoteRepository.getWalletBalances()

    override fun getWalletTransactions(token: String) =
        walletsRemoteRepository.getWalletTransactions(token)

    override fun getToken(token: String) = walletsRemoteRepository.getToken(token)

    override fun getTokens() = walletsRemoteRepository.getTokens()

    override fun sendTokens(sendTokenData: SendTokenData) =
        walletsRemoteRepository.sendTokens(sendTokenData)

    override fun requestTokens(requestData: RequestTokenData) =
        walletsRemoteRepository.requestTokens(requestData)
}