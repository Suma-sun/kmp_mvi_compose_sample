package com.suma.kmp_mvi_compose_sample.platform

import android.content.res.Resources
import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()


actual fun currentDeviceStaticInfo() = DeviceStaticInfo(
    "Android", "${Build.VERSION.RELEASE} (API ${Build.VERSION.SDK_INT})",
    "${Build.MANUFACTURER} ${Build.MODEL}", Build.SUPPORTED_ABIS.firstOrNull() ?: "unknown")

actual fun currentDeviceMetrics(): DeviceMetrics {
    val m = Resources.getSystem().displayMetrics   // 旋转后可能滞后，故不缓存
    return DeviceMetrics(m.widthPixels, m.heightPixels, m.density)
}