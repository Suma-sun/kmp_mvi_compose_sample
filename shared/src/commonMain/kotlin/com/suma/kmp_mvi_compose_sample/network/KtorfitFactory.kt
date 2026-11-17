package com.suma.kmp_mvi_compose_sample.network

import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient

fun createKtorfit(baseUrl: String, httpClient: HttpClient): Ktorfit =
    Ktorfit.Builder()
        .baseUrl(baseUrl)
        .httpClient(httpClient)
        .build()