package com.toucanwalletdemo.data.repositories

import com.toucanwalletdemo.data.models.RegistrationReward
import com.toucanwalletdemo.data.models.Reward
import com.toucanwalletdemo.data.models.RewardData
import com.toucanwalletdemo.data.models.SimpleResponse
import io.reactivex.Observable

interface RegisterRewardRepository {

    fun getRegisterReward(): Observable<RegistrationReward>

    fun claimRegisterReward(rewardData: RewardData): Observable<SimpleResponse>

    fun saveReward(reward: Reward?)

    fun getSavedReward(): Reward?

    fun deleteReward()
}