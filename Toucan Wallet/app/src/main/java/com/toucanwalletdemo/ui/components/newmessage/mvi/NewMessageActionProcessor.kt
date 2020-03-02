package com.toucanwalletdemo.ui.components.newmessage.mvi

import android.content.Context
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.repositories.UserRepository
import com.toucanwalletdemo.ui.base.mvi.*
import com.toucanwalletdemo.ui.components.newmessage.mvi.NewMessageAction.MessageSendAction
import com.toucanwalletdemo.ui.components.newmessage.mvi.NewMessageResult.*
import io.reactivex.Observable
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class NewMessageActionProcessor: MviActionsProcessor<NewMessageAction, NewMessageResult>(), KoinComponent {

    private val schedulersProvider: SchedulersProvider by inject()
    private val userRepository: UserRepository by inject()
    private val context: Context by inject()

    override fun getActionProcessors(shared: Observable<NewMessageAction>) = listOf(
        shared.connect(sendMessageActionProcessor)
    )

    private val sendMessageActionProcessor = createActionProcessor<MessageSendAction, NewMessageResult>(
        schedulersProvider,
        { InFlight },
        { Error(it.localizedMessage) }) {
        when {
            it.targetUsername.isBlank() -> onNextSafe(Error(context.getString(R.string.validation_error_username_empty)))
            it.targetUsername == userRepository.getUsername() -> onNextSafe(Error(context.getString(R.string.validation_error_same_user)))
            else ->
                onNextSafe(MoveToMessagesThread(it.targetUsername))
        }
        onCompleteSafe()
    }
}