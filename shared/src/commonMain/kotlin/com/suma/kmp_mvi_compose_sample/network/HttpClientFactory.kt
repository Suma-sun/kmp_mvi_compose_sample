package com.suma.kmp_mvi_compose_sample.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json


fun createHttpClient(): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(networkJson)
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 10_000
        requestTimeoutMillis = 15_000
        socketTimeoutMillis = 10_000
    }
    // 可在此追加 Logging、UserAgent 等插件
}