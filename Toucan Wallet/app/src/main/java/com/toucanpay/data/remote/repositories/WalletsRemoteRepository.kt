package com.toucanpay.data.remote.repositories

import com.toucanpay.data.models.*
import io.reactivex.Observable

interface WalletsRemoteRepository {

    fun getWalletBalances(): Observable<List<Balance>>

    fun getWalletTransactions(token: String): Observable<List<Transaction>>

    fun getToken(token: String): Observable<TokenInfo>

    fun getTokens(): Observable<List<Token>>

    fun sendTokens(sendData: SendTokenData): Observable<SimpleResponse>

    fun requestTokens(requestData: RequestTokenData): Observable<SimpleResponse>
}