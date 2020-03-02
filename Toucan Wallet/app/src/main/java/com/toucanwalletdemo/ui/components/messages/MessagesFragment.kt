package com.toucanwalletdemo.ui.components.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.messages.messagesAdapter.MessagesAdapter
import com.toucanwalletdemo.ui.components.messages.mvi.MessagesAction
import com.toucanwalletdemo.ui.components.messages.mvi.MessagesResult
import kotlinx.android.synthetic.main.fragment_messages.*

class MessagesFragment: MviBaseFragment<MessagesAction, MessagesResult, MessagesViewState, MessagesViewModel>(
    MessagesViewModel::class.java
) {

    private val messageAdapter = MessagesAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_messages, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMessagesRecyclerView()

        newMessageFab.setOnClickListener {
            navController.navigate(R.id.navigateToNewMessageFragment)
        }
    }

    private fun initMessagesRecyclerView() {
        messageAdapter.onItemClickListener = {
            navController.navigate(MessagesFragmentDirections.navigateToMessagesThreadFragment(it))
        }
        messagesRecyclerView.adapter = messageAdapter
    }

    override fun render(viewState: MessagesViewState) {
        with(viewState) {
            emptyMessageListText.isInvisible = messages.isNullOrEmpty()
            emptyState.isVisible = messages.isNullOrEmpty() && !inProgress

            if (!messages.isNullOrEmpty()) {
                messageAdapter.setMessages(messages)
            }
        }
    }
}