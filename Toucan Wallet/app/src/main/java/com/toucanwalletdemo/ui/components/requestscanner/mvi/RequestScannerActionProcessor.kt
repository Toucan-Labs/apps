package com.toucanwalletdemo.ui.components.requestscanner.mvi

import android.content.Context
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.RequestTokenData
import com.toucanwalletdemo.data.repositories.WalletsRepository
import com.toucanwalletdemo.ui.base.mvi.MviActionsProcessor
import com.toucanwalletdemo.ui.base.mvi.SchedulersProvider
import com.toucanwalletdemo.ui.components.requestscanner.mvi.RequestScannerResult.*
import com.toucanwalletdemo.ui.model.TradeInputData
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.IOException

class RequestScannerActionProcessor: MviActionsProcessor<RequestScannerAction, RequestScannerResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val walletsRepository: WalletsRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<RequestScannerAction>) = listOf(
        shared.connect(sendTokensActionProcessor)
    )

    private val sendTokensActionProcessor = ObservableTransformer<RequestScannerAction.RequestTokensAction, RequestScannerResult> { action ->
        action.flatMap { data ->
            with(data.data) {
                walletsRepository.requestTokens(RequestTokenData(signature, random, username, amount, tokenSymbol, reference))
                    .observeOn(schedulersProvider.observableScheduler())
                    .subscribeOn(schedulersProvider.subscriptionScheduler())
                    .map { response ->
                        with(response) {
                            when {
                                !error.isNullOrEmpty() && error == context.getString(R.string.trade_error_insufficient_balance) ->
                                    Error(context.getString(R.string.trade_error_not_enough_tokens))
                                !error.isNullOrEmpty() -> Error(error)
                                else -> Success(TradeInputData(username, tokenSymbol, amount, reference))
                            }
                        }
                    }
                    .cast(RequestScannerResult::class.java)
                    .startWith(InFlight)
                    .onErrorReturn {
                        if (it is IOException) {
                            Error(context.getString(R.string.trade_error_no_internet))
                        } else {
                            Error(it.localizedMessage)
                        }
                    }
            }
        }
    }
}