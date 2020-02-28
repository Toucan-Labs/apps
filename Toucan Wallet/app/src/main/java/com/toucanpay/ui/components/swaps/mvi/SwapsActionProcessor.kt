package com.toucanpay.ui.components.swaps.mvi

import android.content.Context
import com.toucanpay.R
import com.toucanpay.data.models.SwapRequestData
import com.toucanpay.data.repositories.SwapsRepository
import com.toucanpay.data.repositories.WalletsRepository
import com.toucanpay.ui.base.mvi.MviActionsProcessor
import com.toucanpay.ui.base.mvi.SchedulersProvider
import com.toucanpay.ui.components.swaps.mvi.SwapsResult.*
import com.toucanpay.ui.model.SwapError
import com.toucanpay.ui.model.SwapSuccess
import com.toucanpay.utils.extensions.asObservable
import com.toucanpay.utils.utils.Status
import com.toucanpay.utils.utils.getTokenError
import com.toucanpay.utils.utils.getTokenSuccess
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class SwapsActionProcessor: MviActionsProcessor<SwapsAction, SwapsResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val swapsRepository: SwapsRepository by inject()
    private val walletsRepository: WalletsRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<SwapsAction>) = listOf(
        shared.connect(getTradesActionProcessor),
        shared.connect(rejectTradeActionProcessor),
        shared.connect(fulfilTradeActionProcessor)
    )

    private val getTradesActionProcessor = ObservableTransformer<SwapsAction.GetTradesAction, SwapsResult> { action ->
        action.flatMap {
            swapsRepository.getTrades()
                .observeOn(schedulersProvider.observableScheduler())
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .map { list ->
                    list.filter {
                        it.requests.isNotEmpty() && (it.requests[0].status == Status.PENDING.status ||
                                it.requests[1].status == Status.PENDING.status)
                    }
                }
                .flatMap { trades ->
                    walletsRepository.getTokens()
                        .observeOn(schedulersProvider.observableScheduler())
                        .subscribeOn(schedulersProvider.subscriptionScheduler())
                        .map { tokens ->
                            when {
                                tokens.isNullOrEmpty() -> Error(context.getString(R.string.swaps_error_tokens))
                                else -> Success(trades, tokens)
                            }
                        }
                }
                .cast(SwapsResult::class.java)
                .startWith(InFlight)
                .onErrorReturn { Error(it.localizedMessage) }
        }
    }

    private val rejectTradeActionProcessor = ObservableTransformer<SwapsAction.RejectTradeAction, SwapsResult> { action ->
        action.flatMap { data ->
            swapsRepository.rejectTrade(SwapRequestData(data.trades!![data.position].id))
                .observeOn(schedulersProvider.observableScheduler())
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .map {
                    when {
                        !it.error.isNullOrEmpty() -> TradeRequestError(SwapError(message = it.error))
                        !it.message.isNullOrEmpty() -> {
                            val list = data.trades.toMutableList()
                            list.removeAt(data.position)
                            TradeRequestSuccess(SwapSuccess(message = it.message), list)
                        }
                        else -> TradeRequestError(SwapError(message = context.getString(R.string.swaps_error_occurred)))
                    }
                }
                .cast(SwapsResult::class.java)
                .startWith(InFlightTrades)
                .onErrorReturn { TradeRequestError(SwapError(message = it.localizedMessage)) }
        }
    }

    private val fulfilTradeActionProcessor = ObservableTransformer<SwapsAction.FulfilTradeAction, SwapsResult> { action ->
        action.flatMap { data ->
            swapsRepository.fulfilTrade(SwapRequestData(data.trades!![data.position].id))
                .observeOn(schedulersProvider.observableScheduler())
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .flatMap { response ->
                    when {
                        !response.error.isNullOrEmpty() && response.error == context.getString(R.string.trade_error_insufficient_balance) -> {
                            val tokenError = getTokenError(context, data.trades[data.position].requests, data.tokens)
                            TradeRequestError(SwapError(tokenError, null)).asObservable()
                        }
                        !response.error.isNullOrEmpty() -> TradeRequestError(SwapError(message = response.error)).asObservable()
                        !response.message.isNullOrEmpty() -> {
                            swapsRepository.getTrades()
                                .observeOn(schedulersProvider.observableScheduler())
                                .subscribeOn(schedulersProvider.subscriptionScheduler())
                                .map { list ->
                                    val trade = list.firstOrNull { it.id == data.trades[data.position].id }
                                    when {
                                        trade == null -> TradeRequestError(SwapError(message = context.getString(R.string.swaps_cannot_find_trade)))
                                        trade.requests[1].status == Status.DEPOSITED.status -> TradeRequestSuccess(SwapSuccess(message = response.message), data.trades)
                                        else -> {
                                            val tokenSuccess = getTokenSuccess(context, data.trades[data.position].requests, data.tokens)
                                            val trades = data.trades.toMutableList()
                                            trades.removeAt(data.position)
                                            TradeRequestSuccess(SwapSuccess(tokenSuccess, null), trades)
                                        }
                                    }
                                }
                        }
                        else -> TradeRequestError(SwapError(message = context.getString(R.string.swaps_error_occurred))).asObservable()
                    }
                }
                .cast(SwapsResult::class.java)
                .startWith(InFlightTrades)
                .onErrorReturn { TradeRequestError(SwapError(message = it.localizedMessage)) }
        }
    }
}