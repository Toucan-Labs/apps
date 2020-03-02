package com.toucanwalletdemo.data.repositories

import com.toucanwalletdemo.data.models.Message
import com.toucanwalletdemo.data.models.MessageData
import com.toucanwalletdemo.data.models.SimpleResponse
import io.reactivex.Observable

interface MessagesRepository {

    fun getMessages(): Observable<List<Message>>

    fun sendMessage(messageData: MessageData): Observable<SimpleResponse>

    fun getMessagesThread(username: String?): Observable<List<Message>>
}