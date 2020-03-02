package com.toucanwalletdemo.data.remote.repositories

import com.toucanwalletdemo.data.models.RegistrationReward
import com.toucanwalletdemo.data.models.RewardData
import com.toucanwalletdemo.data.models.SimpleResponse
import com.toucanwalletdemo.data.remote.backend.ToucanBackend
import com.toucanwalletdemo.data.remote.backend.request.RewardRequest
import com.toucanwalletdemo.data.remote.repositories.mappers.toRegistrationReward
import com.toucanwalletdemo.data.remote.repositories.mappers.toSimpleResponse
import io.reactivex.Observable

class RegisterRewardRemoteRepositoryImpl(
    private val toucanBackend: ToucanBackend
): RegisterRewardRemoteRepository {

    override fun getRegistrationReward(): Observable<RegistrationReward> =
        toucanBackend.getRegistrationReward().map { it.toRegistrationReward() }

    override fun claimRegistrationReward(rewardData: RewardData): Observable<SimpleResponse> =
        toucanBackend.claimRegistrationReward(RewardRequest(rewardData.secret)).map { it.toSimpleResponse() }
}