package com.toucanpay.data.remote.repositories.mappers

import com.toucanpay.data.models.SimpleResponse
import com.toucanpay.data.remote.backend.response.SimpleBackendResponse

fun SimpleBackendResponse.toSimpleResponse() = SimpleResponse(message, error)