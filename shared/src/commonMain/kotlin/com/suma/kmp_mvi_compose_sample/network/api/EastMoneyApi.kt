package com.suma.kmp_mvi_compose_sample.network.api

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import kotlinx.serialization.Serializable

interface EastMoneyApi {
    @GET("api/qt/clist/get")
    suspend fun getStockList(
        @Query("pn") page: Int = 1,
        @Query("pz") pageSize: Int = 100,
        @Query("fs") fs: String = "m:0+t:6,m:0+t:80",
        @Query("fields") fields: String = "f12,f14,f2,f3,f5,f6"
    ): EastMoneyResponse
}

@Serializable
data class EastMoneyResponse(val data: EastMoneyData?)
@Serializable
data class EastMoneyData(val diff: List<EastMoneyItem>)
@Serializable
data class EastMoneyItem(
    /**
     * 代码
     */
    val f12: String,
    /**
     * 名称
     */
    val f14: String,
    /**
     * 最新价（可能为"-"）
     */
    val f2: Double?,
    /**
     * 涨跌幅（百分比）
     */
    val f3: Double?,
    /**
     * 成交量（手）
     */
    val f5: Long?,
    /**
     * 成交额（元）
     */
    val f6: Double?
)