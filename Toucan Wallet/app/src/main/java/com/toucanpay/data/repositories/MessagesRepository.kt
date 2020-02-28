package com.toucanpay.data.repositories

import com.toucanpay.data.models.Message
import com.toucanpay.data.models.MessageData
import com.toucanpay.data.models.SimpleResponse
import io.reactivex.Observable

interface MessagesRepository {

    fun getMessages(): Observable<List<Message>>

    fun sendMessage(messageData: MessageData): Observable<SimpleResponse>

    fun getMessagesThread(username: String?): Observable<List<Message>>
}