package com.toucanpay.ui.components.sendscanner.mvi

import android.content.Context
import com.toucanpay.R
import com.toucanpay.data.models.SendTokenData
import com.toucanpay.data.repositories.WalletsRepository
import com.toucanpay.ui.base.mvi.MviActionsProcessor
import com.toucanpay.ui.base.mvi.SchedulersProvider
import com.toucanpay.ui.components.sendscanner.mvi.SendScannerResult.*
import com.toucanpay.ui.model.TradeInputData
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.IOException

class SendScannerActionProcessor: MviActionsProcessor<SendScannerAction, SendScannerResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val walletsRepository: WalletsRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<SendScannerAction>) = listOf(
        shared.connect(sendTokensActionProcessor)
    )

    private val sendTokensActionProcessor = ObservableTransformer<SendScannerAction.SendTokensAction, SendScannerResult> { action ->
        action.flatMap { data ->
            with(data.data) {
                walletsRepository.sendTokens(SendTokenData(tokenSymbol, amount, toAccountId, reference))
                    .observeOn(schedulersProvider.observableScheduler())
                    .subscribeOn(schedulersProvider.subscriptionScheduler())
                    .map { response ->
                        with(response) {
                            when {
                                !error.isNullOrEmpty() && error == context.getString(R.string.trade_error_insufficient_balance) ->
                                    Error(context.getString(R.string.trade_error_not_enough_tokens))
                                !error.isNullOrEmpty() -> Error(error)
                                else -> Success(TradeInputData(toAccountId, tokenSymbol, amount, reference))
                            }
                        }
                    }
                    .cast(SendScannerResult::class.java)
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