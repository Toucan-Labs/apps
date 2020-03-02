package com.toucanwalletdemo.ui.components.messages.messagesAdapter

import android.text.format.DateUtils
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.toucanwalletdemo.R
import com.toucanwalletdemo.data.models.Message
import com.toucanwalletdemo.utils.extensions.inflateItem
import com.toucanwalletdemo.utils.extensions.toFormattedString
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_message.*
import java.util.*

class MessagesAdapter: RecyclerView.Adapter<MessageViewHolder>() {

    var onItemClickListener: ((String) -> Unit)? = null
    private var messages = emptyList<Message>()

    fun setMessages(newMessages: List<Message>) {
        messages = newMessages
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MessageViewHolder(parent)

    override fun getItemCount() = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position], ::onItemClicked)
    }

    private fun onItemClicked(address: String) {
        onItemClickListener?.invoke(address)
    }
}

class MessageViewHolder(
    parent: ViewGroup,
    override val containerView: View = parent.inflateItem(R.layout.item_message)
): RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(message: Message, onItemClickListener: (String) -> Unit) = with(message) {

        val username = when (accountUsername) {
            to -> from
            else -> to
        }

        itemView.setOnClickListener { onItemClickListener(username) }

        usernameText.text = username
        messageText.text = content
        setTimestamp(timestamp)
    }

    //TODO: Add time formatter
    private fun setTimestamp(timestamp: Long) = when {
        System.currentTimeMillis() - timestamp * 1000 < MESSAGES_TIME -> timestampText.text =
            DateUtils.getRelativeTimeSpanString(timestamp * 1000)
        else -> timestampText.text = Date(timestamp * 1000).toFormattedString()
    }

    private companion object {
        const val MESSAGES_TIME = 1000 * 60 * 60 * 24
    }
}