package com.toucanwalletdemo.data.repositories

import com.toucanwalletdemo.data.models.RequestTokenData
import com.toucanwalletdemo.data.models.SendTokenData
import com.toucanwalletdemo.data.remote.repositories.WalletsRemoteRepository

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