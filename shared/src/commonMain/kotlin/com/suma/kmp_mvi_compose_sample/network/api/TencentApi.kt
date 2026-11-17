package com.suma.kmp_mvi_compose_sample.network.api

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path

/**
 * 腾讯接口
 */
interface TencentApi {

    /**
     * @param codes 形如 "sh600000,sz000001" 的代码串
     */
    @GET("q={codes}")
    suspend fun getQuotes(@Path("codes") codes: String): String
}