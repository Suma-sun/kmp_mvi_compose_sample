package com.suma.kmp_mvi_compose_sample

import kotlinx.browser.window
import web.navigator.navigator

class JsPlatform: Platform {
    private val userAgent = navigator.userAgent
    private val browserList = listOf("Chrome", "Firefox", "Safari", "Edge")

    override val name: String = userAgent.findAnyOf(browserList, ignoreCase = true)
            ?.let { (startIndex) -> userAgent.substring(startIndex).substringBefore(" ") }
            ?: "Unknown"
}

actual fun getPlatform(): Platform = JsPlatform()

actual fun currentDeviceInfo(): DeviceInfo {
    val screen = window.screen
    // Web 端没有"CPU 架构"概念，用 JS 引擎标识替代
    return DeviceInfo(
        platformName = "Browser (JS)",
        osVersion = window.navigator.userAgent,
        deviceModel = window.navigator.platform,
        cpuAbi = window.navigator.hardwareConcurrency.toString(), // 逻辑核心数
        screenWidthPx = window.innerWidth,
        screenHeightPx = window.innerHeight,
        density = window.devicePixelRatio.toFloat(),
    )
}