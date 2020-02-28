package com.toucanpay.data.repositories

import com.toucanpay.data.models.*
import io.reactivex.Observable

interface UserRepository {

    fun isUserRegistered(): Boolean

    fun isUserVerified(): Boolean

    fun isUserLoggedIn(): Boolean

    fun isRewardClaimed(): Boolean

    fun setUserRegistered()

    fun setUserVerified()

    fun setRewardClaimed()

    fun logoutUser()

    fun userLoggedIn()

    fun createUser(username: String, email: String, pin: String)

    fun deleteUser()

    fun loginUser(login: LoginData): Observable<Credentials>

    fun registerUser(registerData: RegisterData): Observable<SimpleResponse>

    fun verifyUser(verifyData: VerifyData): Observable<SimpleResponse>

    fun resetPin(resetPinData: ResetPinData): Observable<SimpleResponse>

    fun changePin(changePinData: ChangePinData): Observable<SimpleResponse>

    fun getAccountInfo(): Observable<AccountInfo>

    fun savePIN(password: String)

    fun getPIN(): String?

    fun savePrivateKey(privateKey: String)

    fun getPrivateKey(): String?

    fun saveAuthToken(token: String)

    fun getAuthToken(): String?

    fun getIdentifier(): String?

    fun saveReferralCode(code: String)

    fun getReferralCode(): String?

    fun getUsername(): String?

    fun setUsername(username: String)

    fun getUserEmail(): String?

    fun setUserEmail(email: String)

    fun setAcceptMessages(isChecked: Boolean)

    fun getAcceptMessages(): Boolean
}