package com.suma.kmp_mvi_compose_sample

import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import kotlin.experimental.ExperimentalNativeApi

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalForeignApi::class, ExperimentalNativeApi::class)
actual fun currentDeviceInfo(): DeviceInfo {
    val device = UIDevice.currentDevice
    val screen = UIScreen.mainScreen
    return DeviceInfo(
        platformName = device.systemName,
        osVersion = device.systemVersion,
        deviceModel = device.model,          // 模拟器上返回 "iPhone"，真机精确型号需 sysctl
        cpuAbi =  kotlin.native.Platform.cpuArchitecture.name,
        screenWidthPx = screen.bounds.width.toInt(),
        screenHeightPx = screen.bounds.height.toInt(),
        density = screen.scale.toFloat(),
    )
}