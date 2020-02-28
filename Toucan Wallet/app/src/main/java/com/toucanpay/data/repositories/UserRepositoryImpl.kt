package com.toucanpay.data.repositories

import com.toucanpay.data.models.*
import com.toucanpay.data.prefs.UserPreferences
import com.toucanpay.data.remote.repositories.UserRemoteRepository
import com.toucanpay.utils.extensions.fromBase64
import com.toucanpay.utils.extensions.toBase64String
import java.nio.charset.Charset

class UserRepositoryImpl(
    private val remoteRepository: UserRemoteRepository,
    private val rewardRepository: RegisterRewardRepository,
    private val userPreferences: UserPreferences
): UserRepository {

    override fun isUserRegistered() = userPreferences.isUserRegistered

    override fun isUserVerified() = userPreferences.isUserVerified

    override fun isUserLoggedIn() = !userPreferences.shouldUserLoggedIn

    override fun isRewardClaimed() = userPreferences.isUserClaimReward

    override fun logoutUser() {
        userPreferences.shouldUserLoggedIn = true
        userPreferences.authToken = null
    }

    override fun userLoggedIn() {
        userPreferences.shouldUserLoggedIn = false
    }

    override fun saveAuthToken(token: String) {
        userPreferences.authToken = token
    }

    override fun getAuthToken() = userPreferences.authToken

    override fun setUserRegistered() {
        userPreferences.isUserRegistered = true
    }

    override fun setUserVerified() {
        userPreferences.isUserVerified = true
    }

    override fun setRewardClaimed() {
        userPreferences.isUserClaimReward = true
    }

    override fun getAccountInfo() = remoteRepository.getUserInfo()

    override fun savePIN(password: String) {
        userPreferences.pin = password
    }

    override fun getPIN(): String? = userPreferences.pin

    override fun savePrivateKey(privateKey: String) {
        userPreferences.privateKey = privateKey.toByteArray(Charset.defaultCharset()).toBase64String()
    }

    override fun getPrivateKey(): String? =
        userPreferences.privateKey?.fromBase64()?.toString(Charset.defaultCharset())

    override fun createUser(username: String, email: String, pin: String) {
        userPreferences.username = username
        userPreferences.email = email
        userPreferences.pin = pin
    }

    override fun deleteUser() = with(userPreferences) {
        rewardRepository.deleteReward()
        isUserRegistered = false
        isUserVerified = false
        isUserClaimReward = false
        referralCode = null
        privateKey = null
        username = null
        email = null
        pin = null
    }

    override fun getUsername() = userPreferences.username

    override fun setUsername(username: String) {
        userPreferences.username = username
    }

    override fun getUserEmail() = userPreferences.email

    override fun setUserEmail(email: String) {
        userPreferences.email = email
    }

    override fun loginUser(login: LoginData) = remoteRepository.loginUser(login)

    override fun registerUser(registerData: RegisterData) =
        remoteRepository.registerUser(registerData)

    override fun resetPin(resetPinData: ResetPinData) = remoteRepository.resetPin(resetPinData)

    override fun verifyUser(verifyData: VerifyData) = remoteRepository.verifyUser(verifyData)

    override fun changePin(changePinData: ChangePinData) = remoteRepository.changePin(changePinData)

    override fun getIdentifier() = userPreferences.username ?: userPreferences.email

    override fun saveReferralCode(code: String) {
        userPreferences.referralCode = code
    }

    override fun getReferralCode(): String? = userPreferences.referralCode

    override fun setAcceptMessages(isChecked: Boolean) {
        userPreferences.acceptMessages = isChecked
    }

    override fun getAcceptMessages() = userPreferences.acceptMessages
}
