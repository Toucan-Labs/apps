package com.toucanpay.data.remote.repositories

import com.toucanpay.data.models.*
import com.toucanpay.data.remote.backend.ToucanBackend
import com.toucanpay.data.remote.backend.request.SendTokenRequest
import com.toucanpay.data.remote.backend.request.SignatureTokenRequest
import com.toucanpay.data.remote.repositories.mappers.*
import com.toucanpay.data.repositories.UserRepository
import io.reactivex.Observable

class WalletsRemoteRepositoryImpl(
    private val toucanBackend: ToucanBackend,
    private val userRepository: UserRepository
): WalletsRemoteRepository {

    override fun getWalletBalances(): Observable<List<Balance>> =
        toucanBackend.getWalletBalances().map { it.toBalances() }

    override fun getWalletTransactions(token: String): Observable<List<Transaction>> =
        toucanBackend.getWalletTransactions(token).map { it.toTransactions(userRepository.getUsername()) }

    override fun getToken(token: String): Observable<TokenInfo> =
        toucanBackend.getToken(token).map { it.toTokenInfo() }

    override fun getTokens(): Observable<List<Token>> =
        toucanBackend.getTokens().map { it.toTokens() }

    override fun sendTokens(sendData: SendTokenData): Observable<SimpleResponse> = with(sendData) {
        toucanBackend.sendTokens(SendTokenRequest(tokenSymbol, amount.toString(), toAccountId, reference)).map { it.toSimpleResponse() }
    }

    override fun requestTokens(requestData: RequestTokenData): Observable<SimpleResponse> =
        with(requestData) {
            toucanBackend.requestTokens(SignatureTokenRequest(signature, random, username, amount.toString(), tokenSymbol, reference)).map { it.toSimpleResponse() }
        }
}