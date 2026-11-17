package com.suma.kmp_mvi_compose_sample.domain

/**
 * 通用的股票数据模型
 */
data class Stock(
    val code:String,
    val name: String,
    val price: Double,
    val changePercent: Double, // 涨跌幅，百分比数值，如 2.35 表示 2.35%
    val volume: Long,          // 成交量（股）
    val turnover: Double       // 成交额（元）
)
