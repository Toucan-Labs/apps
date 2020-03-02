package com.toucanwalletdemo.ui.components.messagesthread.adapter

import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.Message
import com.toucanwalletdemo.utils.extensions.inflateItem
import com.toucanwalletdemo.utils.extensions.toFormattedDayString
import com.toucanwalletdemo.utils.extensions.toFormattedHourString
import com.toucanwalletdemo.utils.utils.MessageDiffUtil
import com.toucanwalletdemo.utils.utils.dpToPx
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message_thread.*
import kotlinx.android.synthetic.main.view_single_message.*
import kotlinx.android.synthetic.main.view_single_message.view.*
import java.util.*

class MessagesThreadAdapter: RecyclerView.Adapter<MessageThreadViewHolder>() {

    private var messages = emptyList<Message>()
    private var uniqueMessages = emptyList<Message>()

    fun setMessages(newMessages: List<Message>) {
        val diffUtil = DiffUtil.calculateDiff(MessageDiffUtil(messages, newMessages))
        messages.toMutableList().clear()
        messages = newMessages
        diffUtil.dispatchUpdatesTo(this)
    }

    fun setUniqueMessages(newUniqueMessages: List<Message>) {
        val diffUtil = DiffUtil.calculateDiff(MessageDiffUtil(uniqueMessages, newUniqueMessages))
        uniqueMessages.toMutableList().clear()
        uniqueMessages = newUniqueMessages
        diffUtil.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MessageThreadViewHolder(parent)

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: MessageThreadViewHolder, position: Int) {
        holder.bind(messages[position], uniqueMessages)
    }
}

class MessageThreadViewHolder(
    parent: ViewGroup,
    override val containerView: View = parent.inflateItem(R.layout.item_message_thread)
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(message: Message, uniqueMessages: List<Message>) = with(message) {

        with(messageContainer) {
            when (from) {
                accountUsername -> {
                    itemMessageThread.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    messageText.setTextColor(ContextCompat.getColor(context, R.color.white))
                    timeText.setTextColor(ContextCompat.getColor(context, R.color.white))
                    messageContainer.gravity = Gravity.END
                    itemMessageThread.layoutParams = setEndLayoutParams()
                }
                else -> {
                    itemMessageThread.setCardBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    messageText.setTextColor(ContextCompat.getColor(context, R.color.black))
                    timeText.setTextColor(ContextCompat.getColor(context, R.color.graySecondary))
                    messageContainer.gravity = Gravity.START
                    itemMessageThread.layoutParams = setStartLayoutParams()
                }
            }
            timeText.text = Date(timestamp * 1000).toFormattedHourString()
            messageText.text = content
        }

        groupDivider.isGone = !uniqueMessages.contains(message)
        dateText.text = if (uniqueMessages.contains(message)) Date(timestamp * 1000).toFormattedDayString() else ""
    }

    private fun setStartLayoutParams() =
        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            marginStart = dpToPx(20)
            marginEnd = dpToPx(80)
            topMargin = dpToPx(3)
            bottomMargin = dpToPx(3)
        }

    private fun setEndLayoutParams() =
        LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT).apply {
            marginEnd = dpToPx(20)
            marginStart = dpToPx(80)
            topMargin = dpToPx(3)
            bottomMargin = dpToPx(3)
        }
}