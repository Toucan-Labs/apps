package com.toucanwalletdemo.data.remote.repositories.mappers

import com.toucanwalletdemo.data.models.RegistrationReward
import com.toucanwalletdemo.data.models.Reward
import com.toucanwalletdemo.data.remote.backend.response.RegistrationRewardBackendResponse
import com.toucanwalletdemo.data.remote.backend.response.RewardBackendResponse

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
