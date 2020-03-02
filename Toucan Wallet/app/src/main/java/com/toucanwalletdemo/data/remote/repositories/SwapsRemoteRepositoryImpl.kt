package com.toucanwalletdemo.data.remote.repositories

import com.toucanwalletdemo.data.models.SimpleResponse
import com.toucanwalletdemo.data.models.SwapRequestData
import com.toucanwalletdemo.data.models.Trade
import com.toucanwalletdemo.data.remote.backend.ToucanBackend
import com.toucanwalletdemo.data.remote.backend.request.SwapRequest
import com.toucanwalletdemo.data.remote.repositories.mappers.toSimpleResponse
import com.toucanwalletdemo.data.remote.repositories.mappers.toTrades
import com.toucanwalletdemo.data.repositories.UserRepository
import io.reactivex.Observable

class SwapsRemoteRepositoryImpl(
    private val toucanBackend: ToucanBackend,
    private val userRepository: UserRepository
): SwapsRemoteRepository {

    override fun getTrades(): Observable<List<Trade>> =
        toucanBackend.getTrades().map { it.toTrades(userRepository.getUsername()) }

    override fun rejectTrade(swapRequestData: SwapRequestData): Observable<SimpleResponse> =
        toucanBackend.rejectTrade(SwapRequest(swapRequestData.tradeId)).map { it.toSimpleResponse() }

    override fun fulfilTrade(swapRequestData: SwapRequestData): Observable<SimpleResponse> =
        toucanBackend.fulfilTrade(SwapRequest(swapRequestData.tradeId)).map { it.toSimpleResponse() }
}