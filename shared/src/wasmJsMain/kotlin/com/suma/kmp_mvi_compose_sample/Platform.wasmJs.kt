package com.suma.kmp_mvi_compose_sample

import kotlinx.browser.window

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()


@OptIn(ExperimentalWasmJsInterop::class)
actual fun currentDeviceInfo(): DeviceInfo {
    val screen = window.screen
    // Web 端没有"CPU 架构"概念，用 JS 引擎标识替代
    return DeviceInfo(
        platformName = "Browser (Wasm)",
        osVersion = window.navigator.userAgent,
        deviceModel = window.navigator.platform,
        cpuAbi = window.navigator.hardwareConcurrency.toString(), // 逻辑核心数
        screenWidthPx = window.innerWidth,
        screenHeightPx = window.innerHeight,
        density = window.devicePixelRatio.toFloat(),
    )
}