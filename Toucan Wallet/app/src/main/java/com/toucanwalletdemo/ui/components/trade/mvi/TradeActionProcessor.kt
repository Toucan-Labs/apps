package com.toucanwalletdemo.ui.components.trade.mvi

import android.content.Context
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.SendTokenData
import com.toucanwalletdemo.data.repositories.UserRepository
import com.toucanwalletdemo.data.repositories.WalletsRepository
import com.toucanwalletdemo.ui.base.mvi.MviActionsProcessor
import com.toucanwalletdemo.ui.base.mvi.SchedulersProvider
import com.toucanwalletdemo.ui.components.trade.mvi.TradeResult.*
import com.toucanwalletdemo.ui.model.TradeInputData
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import java.io.IOException

class TradeActionProcessor: MviActionsProcessor<TradeAction, TradeResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val walletsRepository: WalletsRepository by inject()
    private val userRepository: UserRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<TradeAction>) = listOf(
        shared.connect(sendTokensActionProcessor),
        shared.connect(getFirstTokenActionProcessor)
    )

    private val getFirstTokenActionProcessor = ObservableTransformer<TradeAction.GetFirstToken, TradeResult> { action ->
        action.flatMap {
            val username = userRepository.getUsername()
            walletsRepository.getTokens()
                .observeOn(schedulersProvider.observableScheduler())
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .map { tokens ->
                    when {
                        tokens.isNullOrEmpty() -> TokenError(context.getString(R.string.trade_token_error))
                        username.isNullOrBlank() -> TokenError(context.getString(R.string.trade_username_error))
                        else -> {
                            val token = tokens.firstOrNull { it.issuer == username }

                            FirstTokenResult(
                                if (token == null) "${tokens[0].tokenSymbol} - ${tokens[0].tokenName}"
                                else "${token.tokenSymbol} - ${token.tokenName}"
                            )
                        }
                    }
                }
                .cast(TradeResult::class.java)
                .onErrorReturn { TokenError(it.localizedMessage) }
        }
    }

    private val sendTokensActionProcessor = ObservableTransformer<TradeAction.SendTokensAction, TradeResult> { action ->
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
                                else -> Success(TradeInputData(toAccountId, tokenSymbol, amount, ""))
                            }
                        }
                    }
                    .cast(TradeResult::class.java)
                    .startWith(InFlight)
                    .onErrorReturn {
                        if (it is IOException) Error(context.getString(R.string.trade_error_no_internet))
                        else Error(it.localizedMessage)
                    }
            }
        }
    }
}