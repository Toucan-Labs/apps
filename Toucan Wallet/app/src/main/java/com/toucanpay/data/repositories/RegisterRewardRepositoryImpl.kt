package com.toucanpay.data.repositories

import com.toucanpay.data.models.Reward
import com.toucanpay.data.models.RewardData
import com.toucanpay.data.prefs.UserPreferences
import com.toucanpay.data.prefs.deserializeReward
import com.toucanpay.data.prefs.serializeReward
import com.toucanpay.data.remote.repositories.RegisterRewardRemoteRepository

class RegisterRewardRepositoryImpl(
    private val registerRewardRemoteRepository: RegisterRewardRemoteRepository,
    private val userPreferences: UserPreferences
): RegisterRewardRepository {

    override fun getRegisterReward() =
        registerRewardRemoteRepository.getRegistrationReward()

    override fun claimRegisterReward(rewardData: RewardData) =
        registerRewardRemoteRepository.claimRegistrationReward(rewardData)

    override fun saveReward(reward: Reward?) {
        userPreferences.reward = reward?.serializeReward()
    }

    override fun getSavedReward(): Reward? = userPreferences.reward?.deserializeReward()

    override fun deleteReward() {
        userPreferences.reward = null
    }
}