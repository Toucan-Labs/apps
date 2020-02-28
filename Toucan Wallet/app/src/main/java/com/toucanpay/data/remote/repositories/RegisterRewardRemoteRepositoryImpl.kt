package com.toucanpay.data.remote.repositories

import com.toucanpay.data.models.RegistrationReward
import com.toucanpay.data.models.RewardData
import com.toucanpay.data.models.SimpleResponse
import com.toucanpay.data.remote.backend.ToucanBackend
import com.toucanpay.data.remote.backend.request.RewardRequest
import com.toucanpay.data.remote.repositories.mappers.toRegistrationReward
import com.toucanpay.data.remote.repositories.mappers.toSimpleResponse
import io.reactivex.Observable

class RegisterRewardRemoteRepositoryImpl(
    private val toucanBackend: ToucanBackend
): RegisterRewardRemoteRepository {

    override fun getRegistrationReward(): Observable<RegistrationReward> =
        toucanBackend.getRegistrationReward().map { it.toRegistrationReward() }

    override fun claimRegistrationReward(rewardData: RewardData): Observable<SimpleResponse> =
        toucanBackend.claimRegistrationReward(RewardRequest(rewardData.secret)).map { it.toSimpleResponse() }
}