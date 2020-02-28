package com.toucanpay.data.remote.repositories

import com.toucanpay.data.models.*
import io.reactivex.Observable

interface UserRemoteRepository {

    fun loginUser(loginData: LoginData): Observable<Credentials>

    fun registerUser(registerData: RegisterData): Observable<SimpleResponse>

    fun verifyUser(verifyData: VerifyData): Observable<SimpleResponse>

    fun resetPin(resetPinData: ResetPinData): Observable<SimpleResponse>

    fun changePin(changePinData: ChangePinData): Observable<SimpleResponse>

    fun getUserInfo(): Observable<AccountInfo>
}