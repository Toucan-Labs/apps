package com.toucanwalletdemo.data.remote.repositories

import com.toucanwalletdemo.data.models.Message
import com.toucanwalletdemo.data.models.MessageData
import com.toucanwalletdemo.data.models.SimpleResponse
import io.reactivex.Observable

interface MessagesRemoteRepository {

    fun getMessages(): Observable<List<Message>>

    fun sendMessage(messageData: MessageData): Observable<SimpleResponse>

    fun getMessagesThread(username: String?): Observable<List<Message>>
}