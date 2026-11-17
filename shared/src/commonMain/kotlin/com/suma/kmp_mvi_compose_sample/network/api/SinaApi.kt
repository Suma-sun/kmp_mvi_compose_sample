package com.suma.kmp_mvi_compose_sample.network.api

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Headers
import de.jensklingenberg.ktorfit.http.Query

/**
 * 新浪接口
 */
interface SinaApi {

    @Headers("Referer: http://finance.sina.com.cn/")
    @GET("list={codes}")
    suspend fun getQuotes(@Query("codes") codes: String): String
}