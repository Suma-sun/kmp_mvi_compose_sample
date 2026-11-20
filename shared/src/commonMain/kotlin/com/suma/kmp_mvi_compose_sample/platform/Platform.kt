package com.suma.kmp_mvi_compose_sample.platform


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform



expect fun currentDeviceStaticInfo(): DeviceStaticInfo

expect fun currentDeviceMetrics(): DeviceMetrics