package com.toucanwalletdemo.data.remote.repositories

import com.toucanwalletdemo.data.models.SimpleResponse
import com.toucanwalletdemo.data.models.SwapRequestData
import com.toucanwalletdemo.data.models.Trade
import io.reactivex.Observable

interface SwapsRemoteRepository {

    fun getTrades(): Observable<List<Trade>>

    fun rejectTrade(swapRequestData: SwapRequestData): Observable<SimpleResponse>

    fun fulfilTrade(swapRequestData: SwapRequestData): Observable<SimpleResponse>
}