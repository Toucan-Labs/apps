package com.toucanwalletdemo.data.remote.repositories

import com.toucanwalletdemo.data.models.*
import com.toucanwalletdemo.data.remote.backend.ToucanBackend
import com.toucanwalletdemo.data.remote.backend.request.*
import com.toucanwalletdemo.data.remote.repositories.mappers.toAccountInfo
import com.toucanwalletdemo.data.remote.repositories.mappers.toCredentials
import com.toucanwalletdemo.data.remote.repositories.mappers.toSimpleResponse
import io.reactivex.Observable

class UserRemoteRepositoryImpl(
    private val toucanBackend: ToucanBackend
): UserRemoteRepository {

    override fun loginUser(loginData: LoginData): Observable<Credentials> = with(loginData) {
        toucanBackend.login(LoginRequest(identifier, password)).map { it.toCredentials() }
    }

    override fun registerUser(registerData: RegisterData): Observable<SimpleResponse> =
        with(registerData) {
            toucanBackend.register(RegisterRequest(email, password, username, invitationReferralCode)).map { it.toSimpleResponse() }
        }

    override fun verifyUser(verifyData: VerifyData): Observable<SimpleResponse> = with(verifyData) {
        toucanBackend.verify(VerifyRequest(email, code)).map { it.toSimpleResponse() }
    }

    override fun resetPin(resetPinData: ResetPinData): Observable<SimpleResponse> =
        toucanBackend.resetPin(ResetPinRequest(resetPinData.identifier)).map { it.toSimpleResponse() }

    override fun changePin(changePinData: ChangePinData): Observable<SimpleResponse> =
        with(changePinData) {
            toucanBackend.changePin(ChangePinRequest(emailPassword, newPassword)).map { it.toSimpleResponse() }
        }

    override fun getUserInfo(): Observable<AccountInfo> =
        toucanBackend.getAccountInfo().map { it.toAccountInfo() }
}