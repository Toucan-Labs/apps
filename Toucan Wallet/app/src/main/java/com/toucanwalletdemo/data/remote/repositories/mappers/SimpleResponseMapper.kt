package com.toucanwalletdemo.data.remote.repositories.mappers

import com.toucanwalletdemo.data.models.SimpleResponse
import com.toucanwalletdemo.data.remote.backend.response.SimpleBackendResponse

fun SimpleBackendResponse.toSimpleResponse() = SimpleResponse(message, error)