package com.toucanpay.data.remote.backend

import com.toucanpay.data.remote.backend.request.*
import com.toucanpay.data.remote.backend.response.*
import io.reactivex.Observable
import retrofit2.http.*

interface ToucanBackend {

    @POST("account/login")
    @Headers("$NO_AUTHENTICATION_HEADER: true")
    fun login(@Body loginRequest: LoginRequest): Observable<CredentialsBackendResponse>

    @POST("account/register")
    @Headers("$NO_AUTHENTICATION_HEADER: true")
    fun register(@Body registerRequest: RegisterRequest): Observable<SimpleBackendResponse>

    @POST("account/verify")
    @Headers("$NO_AUTHENTICATION_HEADER: true")
    fun verify(@Body verifyRequest: VerifyRequest): Observable<SimpleBackendResponse>

    @POST("account/resetPassword")
    @Headers("$NO_AUTHENTICATION_HEADER: true")
    fun resetPin(@Body resetRequest: ResetPinRequest): Observable<SimpleBackendResponse>

    @POST("account/changePassword")
    fun changePin(@Body changeRequest: ChangePinRequest): Observable<SimpleBackendResponse>

    @GET("message/threads")
    fun getMessagesList(): Observable<MessagesBackendResponse>

    @GET("account/info")
    fun getAccountInfo(): Observable<AccountInfoBackendResponse>

    @GET("reward")
    fun getRegistrationReward(): Observable<RegistrationRewardBackendResponse>

    @POST("reward")
    fun claimRegistrationReward(@Body rewardRequest: RewardRequest): Observable<SimpleBackendResponse>

    @POST("message/send")
    fun sendMessage(@Body messageRequest: MessageRequest): Observable<SimpleBackendResponse>

    @GET("message/{username}")
    fun getMessagesThread(@Path("username") username: String?): Observable<MessagesBackendResponse>

    @GET("wallet/balances")
    fun getWalletBalances(): Observable<BalancesBackendResponse>

    @GET("transactions/{token}")
    fun getWalletTransactions(@Path("token") token: String): Observable<TransactionsBackendResponse>

    @GET("network/token/{tokenSymbol}")
    fun getToken(@Path("tokenSymbol") tokenSymbol: String): Observable<TokenInfoBackendResponse>

    @GET("network/tokens")
    fun getTokens(): Observable<TokenListInfoBackendResponse>

    @POST("/wallet/sendTokens")
    fun sendTokens(@Body sendRequest: SendTokenRequest): Observable<SimpleBackendResponse>

    @POST("/offline/transfer")
    fun requestTokens(@Body signatureRequest: SignatureTokenRequest): Observable<SimpleBackendResponse>

    @GET("/trade/all")
    fun getTrades(): Observable<TradesBackendResponse>

    @POST("/trade/reject")
    fun rejectTrade(@Body swapRequest: SwapRequest): Observable<SimpleBackendResponse>

    @POST("/trade/fulfil")
    fun fulfilTrade(@Body swapRequest: SwapRequest): Observable<SimpleBackendResponse>

    companion object {
        const val NO_AUTHENTICATION_HEADER = "NO_AUTHENTICATION_HEADER"
    }
}