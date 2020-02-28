package com.toucanpay.data.remote.repositories

import com.toucanpay.data.models.SimpleResponse
import com.toucanpay.data.models.SwapRequestData
import com.toucanpay.data.models.Trade
import io.reactivex.Observable

interface SwapsRemoteRepository {

    fun getTrades(): Observable<List<Trade>>

    fun rejectTrade(swapRequestData: SwapRequestData): Observable<SimpleResponse>

    fun fulfilTrade(swapRequestData: SwapRequestData): Observable<SimpleResponse>
}