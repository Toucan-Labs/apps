package com.toucanwalletdemo.data.repositories

import com.toucanwalletdemo.data.models.SwapRequestData
import com.toucanwalletdemo.data.remote.repositories.SwapsRemoteRepository

class SwapsRepositoryImpl(
    private val swapsRemoteRepository: SwapsRemoteRepository
): SwapsRepository {

    override fun getTrades() =
        swapsRemoteRepository.getTrades()

    override fun rejectTrade(swapRequestData: SwapRequestData) =
        swapsRemoteRepository.rejectTrade(swapRequestData)

    override fun fulfilTrade(swapRequestData: SwapRequestData) =
        swapsRemoteRepository.fulfilTrade(swapRequestData)
}