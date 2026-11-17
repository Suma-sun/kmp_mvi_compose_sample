package com.suma.kmp_mvi_compose_sample.network

import kotlinx.serialization.json.Json

/**
 * Json序列化配置
 */
val networkJson = Json {
    isLenient = true
    ignoreUnknownKeys = true
    encodeDefaults = true
    prettyPrint = false
}