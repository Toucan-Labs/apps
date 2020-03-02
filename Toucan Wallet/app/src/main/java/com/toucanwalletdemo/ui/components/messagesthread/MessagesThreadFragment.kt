package com.toucanwalletdemo.ui.components.messagesthread

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.toucanwalletdemo.R
import com.toucanwalletdemo.ui.base.mvi.MviBaseFragment
import com.toucanwalletdemo.ui.components.messagesthread.adapter.MessagesThreadAdapter
import com.toucanwalletdemo.ui.components.messagesthread.mvi.MessagesThreadAction
import com.toucanwalletdemo.ui.components.messagesthread.mvi.MessagesThreadResult
import com.toucanwalletdemo.utils.SimpleTextWatcher
import com.toucanwalletdemo.utils.utils.hideSoftInput
import kotlinx.android.synthetic.main.fragment_messages_thread.*

class MessagesThreadFragment: MviBaseFragment<MessagesThreadAction, MessagesThreadResult, MessagesThreadViewState, MessagesThreadViewModel>(
    MessagesThreadViewModel::class.java
) {

    private val messageThreadAdapter = MessagesThreadAdapter()
    private val args by navArgs<MessagesThreadFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_messages_thread, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        initMessagesThreadRecyclerView()
        sendMessageButton.isEnabled = false
        messageTextWatcher()

        viewModel.initFilterMessages(args.username)
        messagesThreadTitle.text = args.username

        closeButton.setOnClickListener {
            hideSoftInput(requireContext(), view)
            navController.navigateUp()
        }

        sendMessageButton.setOnClickListener {
            viewModel.sendMessage(
                args.username,
                messageInput.text.toString()
            )
            messageInput.text.clear()
        }
    }

    private fun initMessagesThreadRecyclerView() = with(messagesThreadRecyclerView) {
        adapter = messageThreadAdapter
        addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom) {
                postDelayed({ smoothScrollToPosition(0) }, 300)
            }
        }
    }

    private fun messageTextWatcher() =
        messageInput.addTextChangedListener(object: SimpleTextWatcher() {
            override fun onTextChanged(charSequence: CharSequence, start: Int, before: Int, count: Int) {
                sendMessageButton.isEnabled = charSequence.isNotEmpty()
            }
        })

    override fun render(viewState: MessagesThreadViewState) {
        with(viewState) {

            if (!messages.isNullOrEmpty() && !uniqueMessages.isNullOrEmpty()) {
                messageThreadAdapter.setMessages(messages)
                messageThreadAdapter.setUniqueMessages(uniqueMessages)
                messagesThreadRecyclerView.scrollToPosition(0)
            }

            error?.consume {
                it?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun hasBottomBar(): Boolean = false
}