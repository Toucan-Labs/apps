package com.toucanpay.data.remote.backend

import com.toucanpay.data.repositories.UserRepository
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthenticationInterceptor(
    private val lazyUserRepository: Lazy<UserRepository>
): Interceptor {

    private val userRepository by lazy { lazyUserRepository.value }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        /** No authentication required */
        if (request.header(ToucanBackend.NO_AUTHENTICATION_HEADER) != null) {
            return chain.proceed(request)
        }

        val authToken = userRepository.getAuthToken()

        return chain.proceed(request.withAccessToken(authToken!!))
    }
}

private fun Request.withAccessToken(accessToken: String): Request {
    return newBuilder()
        .header("Authorization", "bearer $accessToken")
        .build()
}