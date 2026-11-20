package com.suma.kmp_mvi_compose_sample.platform

import kotlinx.browser.window

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()


@OptIn(ExperimentalWasmJsInterop::class)
actual fun currentDeviceStaticInfo(): DeviceStaticInfo = DeviceStaticInfo(
    platformName = "Browser (Wasm)",
    osVersion = window.navigator.userAgent,
    deviceModel = window.navigator.platform,
    cpuAbi = window.navigator.hardwareConcurrency.toString(),
)

actual fun currentDeviceMetrics(): DeviceMetrics {
    val dpr = window.devicePixelRatio.toFloat()
    return DeviceMetrics(
        widthPx = window.innerWidth,
        heightPx = window.innerHeight,
        density = dpr,
    )
}