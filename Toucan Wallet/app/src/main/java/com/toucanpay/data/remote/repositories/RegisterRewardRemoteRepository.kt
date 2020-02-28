package com.toucanpay.data.remote.repositories

import com.toucanpay.data.models.RegistrationReward
import com.toucanpay.data.models.RewardData
import com.toucanpay.data.models.SimpleResponse
import io.reactivex.Observable

interface RegisterRewardRemoteRepository {

    fun getRegistrationReward(): Observable<RegistrationReward>

    fun claimRegistrationReward(rewardData: RewardData): Observable<SimpleResponse>
}