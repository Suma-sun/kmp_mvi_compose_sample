package com.suma.kmp_mvi_compose_sample.platform

/** 进程内不变的部分：可以安全缓存 */
data class DeviceStaticInfo(
    val platformName: String,
    val osVersion: String,
    val deviceModel: String,
    val cpuAbi: String,
)

/** 随配置变化的部分：不要长期缓存 */
data class DeviceMetrics(
    val widthPx: Int,
    val heightPx: Int,
    val density: Float,
)