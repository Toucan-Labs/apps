package com.toucanwalletdemo.ui.components.wallets.mvi

import android.content.Context
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.repositories.UserRepository
import com.toucanwalletdemo.data.repositories.WalletsRepository
import com.toucanwalletdemo.ui.base.mvi.MviActionsProcessor
import com.toucanwalletdemo.ui.base.mvi.SchedulersProvider
import com.toucanwalletdemo.ui.components.wallets.mvi.WalletsAction.GetBalancesAction
import com.toucanwalletdemo.ui.components.wallets.mvi.WalletsResult.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class WalletsActionProcessor: MviActionsProcessor<WalletsAction, WalletsResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val walletsRepository: WalletsRepository by inject()
    private val userRepository: UserRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<WalletsAction>) = listOf(
        shared.connect(getBalancesActionProcessor)
    )

    private val getBalancesActionProcessor = ObservableTransformer<GetBalancesAction, WalletsResult> { action ->
        action.flatMap {
            val username = userRepository.getUsername()
            walletsRepository.getWalletBalances()
                .observeOn(schedulersProvider.observableScheduler())
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .map {
                    when {
                        username.isNullOrBlank() -> Error(context.getString(R.string.wallets_username_error))
                        else -> {
                            val list = it.filter { balance -> balance.tokenName == username }
                            Success(if (list.isEmpty()) it else list)
                        }
                    }
                }
                .cast(WalletsResult::class.java)
                .startWith(InFlight)
                .onErrorReturn { Error(it.localizedMessage) }
        }
    }
}