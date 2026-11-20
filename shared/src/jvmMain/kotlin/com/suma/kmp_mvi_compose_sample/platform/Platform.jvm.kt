package com.suma.kmp_mvi_compose_sample.platform

import java.awt.GraphicsEnvironment
import java.awt.Toolkit

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()


actual fun currentDeviceStaticInfo(): DeviceStaticInfo = DeviceStaticInfo(
    platformName = "JVM Desktop",
    osVersion = "${System.getProperty("os.name")} ${System.getProperty("os.version")}",
    deviceModel = System.getProperty("os.name") ?: "Desktop",
    cpuAbi = System.getProperty("os.arch") ?: "unknown",   // x86_64 / aarch64
)

actual fun currentDeviceMetrics(): DeviceMetrics {
    val (w, h) = runCatching {
        val size = Toolkit.getDefaultToolkit().screenSize
        size.width to size.height
    }.getOrDefault(0 to 0)

    return DeviceMetrics(
        widthPx = w,
        heightPx = h,
        density = desktopDensity(),
    )
}

/**
 * Desktop 没有统一的 density 概念：
 * 用屏幕 DPI 相对 72dpi 基线折算，拿不到就退化为 1f。
 * headless 环境（如 CI 跑测试）会抛异常，故 runCatching 兜底。
 */
private fun desktopDensity(): Float = runCatching {
    val dpi = Toolkit.getDefaultToolkit().getScreenResolution()
    if (dpi > 0) dpi / 72f else 1f
}.getOrDefault(1f)

/** 可选：多屏场景下取主屏尺寸，比 Toolkit.screenSize 更明确 */
private fun primaryScreenSize(): Pair<Int, Int> = runCatching {
    val cfg = GraphicsEnvironment.getLocalGraphicsEnvironment()
        .defaultScreenDevice.defaultConfiguration
    val b = cfg.bounds
    b.width to b.height
}.getOrDefault(0 to 0)