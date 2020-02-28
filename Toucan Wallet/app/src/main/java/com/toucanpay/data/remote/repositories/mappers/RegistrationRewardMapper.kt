package com.toucanpay.data.remote.repositories.mappers

import com.toucanpay.data.models.RegistrationReward
import com.toucanpay.data.models.Reward
import com.toucanpay.data.remote.backend.response.RegistrationRewardBackendResponse
import com.toucanpay.data.remote.backend.response.RewardBackendResponse

fun RegistrationRewardBackendResponse.toRegistrationReward() = RegistrationReward(
    message?.toReward(),
    error
)

fun RewardBackendResponse.toReward(): Reward? {
    tokenSymbol ?: return null

    return Reward(
        secret ?: "",
        amount ?: "0.0",
        tokenSymbol,
        status ?: ""
    )
}
