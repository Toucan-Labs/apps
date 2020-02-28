package com.toucanpay.data.remote.repositories.mappers

import com.toucanpay.data.models.Message
import com.toucanpay.data.remote.backend.response.MessageBackendResponse
import com.toucanpay.data.remote.backend.response.MessagesBackendResponse

fun MessagesBackendResponse.toMessages(accountUsername: String?): List<Message> =
    message.mapNotNull { it.toMessage(accountUsername) }

fun MessageBackendResponse.toMessage(accountUsername: String?): Message? {
    accountUsername ?: return null

    return Message(id, from, to, content, timestamp, accountUsername)
}

