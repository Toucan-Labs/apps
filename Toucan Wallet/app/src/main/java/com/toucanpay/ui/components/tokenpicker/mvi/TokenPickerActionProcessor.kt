package com.toucanpay.ui.components.tokenpicker.mvi

import android.content.Context
import com.toucanpay.R
import com.toucanpay.data.repositories.UserRepository
import com.toucanpay.data.repositories.WalletsRepository
import com.toucanpay.ui.base.mvi.MviActionsProcessor
import com.toucanpay.ui.base.mvi.SchedulersProvider
import com.toucanpay.ui.components.tokenpicker.mvi.TokenPickerResult.*
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class TokenPickerActionProcessor: MviActionsProcessor<TokenPickerAction, TokenPickerResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val walletsRepository: WalletsRepository by inject()
    private val userRepository: UserRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<TokenPickerAction>) = listOf(
        shared.connect(getTokensActionProcessor)
    )

    private val getTokensActionProcessor = ObservableTransformer<TokenPickerAction.GetTokensAction, TokenPickerResult> { action ->
        action.flatMap {
            val username = userRepository.getUsername()
            walletsRepository.getTokens()
                .observeOn(schedulersProvider.observableScheduler())
                .subscribeOn(schedulersProvider.subscriptionScheduler())
                .map { tokens ->
                    when {
                        username.isNullOrBlank() -> Error(context.getString(R.string.trade_username_error))
                        tokens.isNullOrEmpty() -> Error(context.getString(R.string.trade_token_error))
                        else -> {
                            val list = tokens.filter { it.issuer == username }
                            Success(if (list.isEmpty()) tokens else list)
                        }
                    }
                }
                .cast(TokenPickerResult::class.java)
                .startWith(InFlight)
                .onErrorReturn { Error(it.localizedMessage) }
        }
    }
}