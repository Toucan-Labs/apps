package com.toucanpay.ui.components.claimbonus.mvi

import android.content.Context
import com.toucanpay.R
import com.toucanpay.data.models.RewardData
import com.toucanpay.data.repositories.RegisterRewardRepository
import com.toucanpay.data.repositories.UserRepository
import com.toucanpay.ui.base.mvi.MviActionsProcessor
import com.toucanpay.ui.base.mvi.SchedulersProvider
import com.toucanpay.ui.components.claimbonus.mvi.ClaimBonusAction.ClaimRewardAction
import com.toucanpay.ui.components.claimbonus.mvi.ClaimBonusResult.*
import com.toucanpay.utils.extensions.asObservable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class ClaimBonusActionProcessor: MviActionsProcessor<ClaimBonusAction, ClaimBonusResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val registerRewardRepository: RegisterRewardRepository by inject()
    private val userRepository: UserRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<ClaimBonusAction>) = listOf(
        shared.connect(claimRegistrationRewardActionProcessor)
    )

    private val claimRegistrationRewardActionProcessor = ObservableTransformer<ClaimRewardAction, ClaimBonusResult> { action ->
        action.flatMap {
            val reward = registerRewardRepository.getSavedReward()
            if (reward == null) {
                Error(context.getString(R.string.claim_bonus_reward)).asObservable()
            } else {
                registerRewardRepository.claimRegisterReward(RewardData(reward.secret))
                    .subscribeOn(schedulersProvider.subscriptionScheduler())
                    .observeOn(schedulersProvider.observableScheduler())
                    .map {
                        if (it.error != null) {
                            Error(it.error)
                        } else {
                            userRepository.setRewardClaimed()
                            ClaimRewardSuccess(reward)
                        }
                    }
                    .cast(ClaimBonusResult::class.java)
                    .startWith(InFlight)
                    .onErrorReturn { Error(it.localizedMessage) }
            }
        }
    }
}