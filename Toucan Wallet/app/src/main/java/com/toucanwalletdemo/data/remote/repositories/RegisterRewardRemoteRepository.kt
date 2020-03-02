package com.toucanwalletdemo.data.remote.repositories

import com.toucanwalletdemo.data.models.RegistrationReward
import com.toucanwalletdemo.data.models.RewardData
import com.toucanwalletdemo.data.models.SimpleResponse
import io.reactivex.Observable

interface RegisterRewardRemoteRepository {

    fun getRegistrationReward(): Observable<RegistrationReward>

    fun claimRegistrationReward(rewardData: RewardData): Observable<SimpleResponse>
}