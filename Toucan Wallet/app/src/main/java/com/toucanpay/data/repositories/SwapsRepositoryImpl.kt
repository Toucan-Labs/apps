package com.toucanpay.data.repositories

import com.toucanpay.data.models.SwapRequestData
import com.toucanpay.data.remote.repositories.SwapsRemoteRepository

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