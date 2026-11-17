package com.suma.kmp_mvi_compose_sample

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
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

    // useContents 把 CValue<CGRect> 解包，lambda 的 receiver 就是 CGRect 本身
    val screenWidth = screen.bounds.useContents { size.width.toInt() }
    val screenHeight = screen.bounds.useContents { size.height.toInt() }

    return DeviceInfo(
        platformName = device.systemName,
        osVersion = device.systemVersion,
        deviceModel = device.model,          // 模拟器上返回 "iPhone"，真机精确型号需 sysctl
        cpuAbi =  kotlin.native.Platform.cpuArchitecture.name,
        screenWidthPx = screenWidth,
        screenHeightPx = screenHeight,
        density = screen.scale.toFloat(),
    )
}