package com.suma.kmp_mvi_compose_sample.data

import com.suma.kmp_mvi_compose_sample.domain.Stock
import com.suma.kmp_mvi_compose_sample.network.api.EastMoneyApi
import com.suma.kmp_mvi_compose_sample.network.api.EastMoneyItem
import com.suma.kmp_mvi_compose_sample.network.api.SinaApi
import com.suma.kmp_mvi_compose_sample.network.api.TencentApi

class StockRepository(
    private val eastMoneyApi: EastMoneyApi,
    private val tencentApi: TencentApi,
    private val sinaApi: SinaApi
) {
    /**
     * 腾讯/新浪批量查询所需的预置A股代码（示例用少量代码，生产环境从本地库读取）
     */
    private val fallbackCodes = listOf("sh600000", "sz000001", "sh600519", "sz000858")

    suspend fun getStockList(): Result<List<Stock>> = runCatching {
        trySource { fetchFromEastMoney() }
            ?: trySource { fetchFromTencent() }
            ?: trySource { fetchFromSina() }
            ?: error("所有数据源均不可用")
    }

    private suspend fun <T> trySource(block: suspend () -> T?): T? =
        runCatching { block() }.getOrNull()

    private suspend fun fetchFromEastMoney(): List<Stock>? {
        val response = eastMoneyApi.getStockList(pageSize = 100)
        val items = response.data?.diff ?: return null
        if (items.isEmpty()) return null
        return items.mapNotNull { it.toStockOrNull() }
    }

    private suspend fun fetchFromTencent(): List<Stock>? {
        val raw = tencentApi.getQuotes(fallbackCodes.joinToString(","))
        return convertFromTencent(raw)
    }

    private suspend fun fetchFromSina(): List<Stock>? {
        val raw = sinaApi.getQuotes(fallbackCodes.joinToString(","))
        return convertFromSina(raw)
    }

    private fun EastMoneyItem.toStockOrNull(): Stock? {
        val price = f2?.takeIf { it > 0 } ?: return null
        return Stock(
            code = f12,
            name = f14,
            price = price,
            changePercent = f3 ?: 0.0,
            volume = (f5 ?: 0L) * 100, // “手”转“股”
            turnover = f6 ?: 0.0
        )
    }

    /**
     * 腾讯源转Stock
     */
    private fun convertFromTencent(raw:String?): List<Stock>? {
        raw?:return null
        val result = mutableListOf<Stock>()
        raw.lineSequence().forEach { line ->
            val trimmed = line.trim()
            // 只处理有效的 v_ 行，且必须包含 ="..." 结构
            if (!trimmed.startsWith("v_") || !trimmed.contains("=\"")) return@forEach

            // 提取引号内的主体内容
            val body = trimmed.substringAfter("=\"").substringBefore("\"")
            // 停牌或无数据时，body 可能为空字符串或仅 "1"
            if (body.isBlank() || body == "1") return@forEach

            val fields = body.split("~")
            // 腾讯完整数据至少 38 个字段，不足则跳过
            if (fields.size < 38) return@forEach

            val code = fields.getOrNull(2)?.takeIf { it.isNotBlank() } ?: return@forEach
            val name = fields.getOrNull(1)?.takeIf { it.isNotBlank() } ?: return@forEach
            // 停牌时价格可能为 0，跳过无效数据
            val price = fields.getOrNull(3)?.toDoubleOrNull()?.takeIf { it > 0 } ?: return@forEach
            val changePercent = fields.getOrNull(32)?.toDoubleOrNull() ?: 0.0
            val volumeHands = fields.getOrNull(36)?.toLongOrNull() ?: 0L
            val turnoverWan = fields.getOrNull(37)?.toDoubleOrNull() ?: 0.0

            result += Stock(
                code = code,
                name = name,
                price = price,
                changePercent = changePercent,
                volume = volumeHands * 100,        // 手 → 股
                turnover = turnoverWan * 10_000.0  // 万元 → 元
            )
        }

        return result
    }

    /**
     * 新浪源转Stock
     */
    private fun convertFromSina(raw:String?):List<Stock>? {
        raw?:return null
        val result = mutableListOf<Stock>()
        raw.lineSequence().forEach { line ->
            val trimmed = line.trim()
            // 只处理有效的 var hq_str_ 行
            if (!trimmed.startsWith("var hq_str_") || !trimmed.contains("=\"")) return@forEach

            // 从键名中提取代码：var hq_str_sh600000 → sh600000 → 600000
            val keyPart = trimmed.substringBefore("=\"").removePrefix("var hq_str_")
            val code = keyPart
                .removePrefix("sh")
                .removePrefix("sz")
                .removePrefix("bj")
                .takeIf { it.isNotBlank() } ?: return@forEach

            // 提取引号内的主体内容
            val body = trimmed.substringAfter("=\"").substringBefore("\"")
            // 停牌或无数据时 body 为空
            if (body.isBlank()) return@forEach

            val fields = body.split(",")
            // 至少需要前 10 个字段才能拿到完整数据
            if (fields.size < 10) return@forEach

            val name = fields[0].takeIf { it.isNotBlank() } ?: return@forEach
            val prevClose = fields[2].toDoubleOrNull() ?: 0.0
            // 停牌时当前价可能为 0
            val price = fields[3].toDoubleOrNull()?.takeIf { it > 0 } ?: return@forEach
            val volume = fields[8].toLongOrNull() ?: 0L          // 单位：股
            val turnover = fields[9].toDoubleOrNull() ?: 0.0     // 单位：元

            // 新浪不直接提供涨跌幅，用（当前价 - 昨收）/ 昨收 * 100 计算
            val changePercent = if (prevClose > 0) {
                (price - prevClose) / prevClose * 100.0
            } else {
                0.0 // 新股或异常情况下无昨收，涨跌幅记 0
            }

            result += Stock(
                code = code,
                name = name,
                price = price,
                changePercent = changePercent,
                volume = volume,
                turnover = turnover
            )
        }

        return result
    }
}