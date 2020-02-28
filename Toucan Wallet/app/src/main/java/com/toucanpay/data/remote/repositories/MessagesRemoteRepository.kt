package com.toucanpay.data.remote.repositories

import com.toucanpay.data.models.Message
import com.toucanpay.data.models.MessageData
import com.toucanpay.data.models.SimpleResponse
import io.reactivex.Observable

interface MessagesRemoteRepository {

    fun getMessages(): Observable<List<Message>>

    fun sendMessage(messageData: MessageData): Observable<SimpleResponse>

    fun getMessagesThread(username: String?): Observable<List<Message>>
}