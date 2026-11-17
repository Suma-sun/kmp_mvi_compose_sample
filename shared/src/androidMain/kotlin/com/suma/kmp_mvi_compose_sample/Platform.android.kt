package com.suma.kmp_mvi_compose_sample

import android.content.res.Resources
import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun currentDeviceInfo():DeviceInfo {
    val metrics = Resources.getSystem().displayMetrics
    return DeviceInfo(
        platformName = "Android",
        osVersion = "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
        deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}",
        cpuAbi = Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown",
        screenWidthPx = metrics.widthPixels,
        screenHeightPx = metrics.heightPixels,
        density = metrics.density,
    )
}