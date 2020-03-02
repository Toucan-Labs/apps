package com.toucanwalletdemo.ui.components.claimbonus.mvi

import android.content.Context
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.RewardData
import com.toucanwalletdemo.data.repositories.RegisterRewardRepository
import com.toucanwalletdemo.data.repositories.UserRepository
import com.toucanwalletdemo.ui.base.mvi.MviActionsProcessor
import com.toucanwalletdemo.ui.base.mvi.SchedulersProvider
import com.toucanwalletdemo.ui.components.claimbonus.mvi.ClaimBonusAction.ClaimRewardAction
import com.toucanwalletdemo.ui.components.claimbonus.mvi.ClaimBonusResult.*
import com.toucanwalletdemo.utils.extensions.asObservable
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