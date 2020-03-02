package com.toucanwalletdemo.data.repositories

import com.toucanwalletdemo.data.models.*
import io.reactivex.Observable

interface WalletsRepository {

    fun getWalletBalances(): Observable<List<Balance>>

    fun getWalletTransactions(token: String): Observable<List<Transaction>>

    fun getToken(token: String): Observable<TokenInfo>

    fun getTokens(): Observable<List<Token>>

    fun sendTokens(sendTokenData: SendTokenData): Observable<SimpleResponse>

    fun requestTokens(requestData: RequestTokenData): Observable<SimpleResponse>
}