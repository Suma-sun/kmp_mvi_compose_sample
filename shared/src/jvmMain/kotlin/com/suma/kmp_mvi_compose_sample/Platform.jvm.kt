package com.suma.kmp_mvi_compose_sample

import java.awt.Toolkit

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()


actual fun currentDeviceInfo(): DeviceInfo {
    val toolkit = Toolkit.getDefaultToolkit()
    val screenSize = toolkit.screenSize          // java.awt.Dimension
    val insets = toolkit.getScreenInsets(
        java.awt.GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .defaultScreenDevice
            .defaultConfiguration
    )

    return DeviceInfo(
        platformName = "JVM Desktop",
        osVersion = "${System.getProperty("os.name")} ${System.getProperty("os.version")}",
        deviceModel = "Desktop",
        cpuAbi = System.getProperty("os.arch"),   // 如 "x86_64" / "aarch64"
        screenWidthPx = screenSize.width,
        screenHeightPx = screenSize.height,
        density = 1f,                             // Desktop 没有统一的 density 概念，默认 1
    )
}