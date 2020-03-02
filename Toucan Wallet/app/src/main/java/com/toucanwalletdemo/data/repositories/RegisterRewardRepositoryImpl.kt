package com.toucanwalletdemo.data.repositories

import com.toucanwalletdemo.data.models.Reward
import com.toucanwalletdemo.data.models.RewardData
import com.toucanwalletdemo.data.prefs.UserPreferences
import com.toucanwalletdemo.data.prefs.deserializeReward
import com.toucanwalletdemo.data.prefs.serializeReward
import com.toucanwalletdemo.data.remote.repositories.RegisterRewardRemoteRepository

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