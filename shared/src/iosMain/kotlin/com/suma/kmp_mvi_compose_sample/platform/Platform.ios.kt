package com.suma.kmp_mvi_compose_sample.platform

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import kotlin.experimental.ExperimentalNativeApi

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalNativeApi::class)
actual fun currentDeviceStaticInfo(): DeviceStaticInfo {
    val d = UIDevice.currentDevice
    return DeviceStaticInfo(d.systemName, d.systemVersion, d.model,
        kotlin.native.Platform.cpuArchitecture.name)
}

@OptIn(ExperimentalForeignApi::class)
actual fun currentDeviceMetrics(): DeviceMetrics {
    val s = UIScreen.mainScreen
    val (w, h) = s.bounds.useContents { size.width.toInt() to size.height.toInt() }
    return DeviceMetrics(w, h, s.scale.toFloat())
}