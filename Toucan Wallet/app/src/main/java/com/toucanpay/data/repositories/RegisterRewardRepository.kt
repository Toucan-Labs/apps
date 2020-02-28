package com.toucanpay.data.repositories

import com.toucanpay.data.models.RegistrationReward
import com.toucanpay.data.models.Reward
import com.toucanpay.data.models.RewardData
import com.toucanpay.data.models.SimpleResponse
import io.reactivex.Observable

interface RegisterRewardRepository {

    fun getRegisterReward(): Observable<RegistrationReward>

    fun claimRegisterReward(rewardData: RewardData): Observable<SimpleResponse>

    fun saveReward(reward: Reward?)

    fun getSavedReward(): Reward?

    fun deleteReward()
}