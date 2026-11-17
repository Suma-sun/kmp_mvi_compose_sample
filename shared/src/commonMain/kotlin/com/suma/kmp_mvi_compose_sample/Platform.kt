package com.suma.kmp_mvi_compose_sample


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

/**
 * 平台信息
 */
data class DeviceInfo(
    val platformName: String,   // Android / iOS
    val osVersion: String,      // 系统版本
    val deviceModel: String,    // 机型
    val cpuAbi: String,         // CPU 架构
    val screenWidthPx: Int,
    val screenHeightPx: Int,
    val density: Float,
)
expect fun currentDeviceInfo(): DeviceInfo