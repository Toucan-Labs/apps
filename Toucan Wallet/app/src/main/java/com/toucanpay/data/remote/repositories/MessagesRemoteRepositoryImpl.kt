package com.toucanpay.data.remote.repositories

import com.toucanpay.data.models.Message
import com.toucanpay.data.models.MessageData
import com.toucanpay.data.models.SimpleResponse
import com.toucanpay.data.remote.backend.ToucanBackend
import com.toucanpay.data.remote.backend.request.MessageRequest
import com.toucanpay.data.remote.repositories.mappers.toMessages
import com.toucanpay.data.remote.repositories.mappers.toSimpleResponse
import com.toucanpay.data.repositories.UserRepository
import io.reactivex.Observable

class MessagesRemoteRepositoryImpl(
    private val toucanBackend: ToucanBackend,
    private val userRepository: UserRepository
): MessagesRemoteRepository {

    override fun getMessages(): Observable<List<Message>> =
        toucanBackend.getMessagesList().map { it.toMessages(userRepository.getUsername()) }

    override fun sendMessage(messageData: MessageData): Observable<SimpleResponse> = with(messageData) {
        toucanBackend.sendMessage(MessageRequest(toAccount, message)).map { it.toSimpleResponse() }
    }

    override fun getMessagesThread(username: String?): Observable<List<Message>> =
        toucanBackend.getMessagesThread(username).map { it.toMessages(userRepository.getUsername()) }
}