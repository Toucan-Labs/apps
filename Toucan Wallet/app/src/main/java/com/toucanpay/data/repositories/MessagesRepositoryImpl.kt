package com.toucanpay.data.repositories

import com.toucanpay.data.models.MessageData
import com.toucanpay.data.remote.repositories.MessagesRemoteRepository

class MessagesRepositoryImpl(
    private val messagesRemoteRepository: MessagesRemoteRepository
): MessagesRepository {

    override fun getMessages() = messagesRemoteRepository.getMessages()

    override fun sendMessage(messageData: MessageData) =
        messagesRemoteRepository.sendMessage(messageData)

    override fun getMessagesThread(username: String?) = messagesRemoteRepository.getMessagesThread(username)
}