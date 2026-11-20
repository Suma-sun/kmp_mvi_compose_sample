package com.suma.kmp_mvi_compose_sample.platform

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

actual fun currentDeviceStaticInfo(): DeviceStaticInfo = DeviceStaticInfo(
    platformName = "Browser (JS)",
    osVersion = window.navigator.userAgent,
    deviceModel = window.navigator.platform,          // 已废弃但各浏览器仍可用
    cpuAbi = window.navigator.hardwareConcurrency.toString(), // 逻辑核心数，Web 无真实 ABI
)

actual fun currentDeviceMetrics(): DeviceMetrics {
    val dpr = window.devicePixelRatio.toFloat()
    // innerWidth/innerHeight 是 CSS 像素；要物理像素就乘 dpr
    return DeviceMetrics(
        widthPx = window.innerWidth,
        heightPx = window.innerHeight,
        density = dpr,
    )
}