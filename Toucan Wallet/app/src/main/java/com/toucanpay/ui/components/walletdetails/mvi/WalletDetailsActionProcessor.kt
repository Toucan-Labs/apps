package com.toucanpay.ui.components.walletdetails.mvi

import com.toucanpay.data.repositories.UserRepository
import com.toucanpay.data.repositories.WalletsRepository
import com.toucanpay.ui.base.mvi.MviActionsProcessor
import com.toucanpay.ui.base.mvi.SchedulersProvider
import com.toucanpay.ui.base.mvi.createActionProcessor
import com.toucanpay.ui.base.mvi.onCompleteSafe
import com.toucanpay.ui.components.walletdetails.mvi.WalletDetailsResult.*
import com.toucanpay.utils.extensions.asObservable
import com.toucanpay.utils.utils.Status
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class WalletDetailsActionProcessor: MviActionsProcessor<WalletDetailsAction, WalletDetailsResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val walletsRepository: WalletsRepository by inject()
    private val userRepository: UserRepository by inject()

    override fun getActionProcessors(shared: Observable<WalletDetailsAction>) = listOf(
        shared.connect(refreshDataActionProcessor),
        shared.connect(acceptMessagesActionProcessor)
    )

    private val refreshDataActionProcessor = ObservableTransformer<WalletDetailsAction.GetTransactionsAction, WalletDetailsResult> { action ->
        action.flatMap { data ->
            walletsRepository.getToken(data.tokenSymbol)
                .observeOn(schedulersProvider.observableScheduler())
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .flatMap { tokenInfo ->
                    if (tokenInfo.error != null) {
                        Error(tokenInfo.error).asObservable()
                    } else {
                        walletsRepository.getWalletBalances()
                            .observeOn(schedulersProvider.observableScheduler())
                            .subscribeOn(schedulersProvider.subscriptionScheduler())
                            .map { it.first { list -> list.tokenSymbol == data.tokenSymbol } }
                            .flatMap { balance ->
                                walletsRepository.getWalletTransactions(data.tokenSymbol)
                                    .observeOn(schedulersProvider.observableScheduler())
                                    .subscribeOn(schedulersProvider.subscriptionScheduler())
                                    .map { list ->
                                        val transactions = list.filter {
                                            it.status != Status.REJECTED.status && it.status != Status.CANCELLED.status && it.status != Status.EXPIRED.status
                                        }
                                        Success(transactions, tokenInfo.token, balance.balance, userRepository.getAcceptMessages())
                                    }
                            }
                    }
                }.cast(WalletDetailsResult::class.java)
                .startWith(InFlight)
                .onErrorReturn { Error(it.localizedMessage) }
        }
    }

    private val acceptMessagesActionProcessor = createActionProcessor<WalletDetailsAction.AcceptMessagesAction, WalletDetailsResult> { data ->
        userRepository.setAcceptMessages(data.isChecked)
        onCompleteSafe()
    }
}