package com.suma.kmp_mvi_compose_sample.data

import com.suma.kmp_mvi_compose_sample.platform.DeviceMetrics
import com.suma.kmp_mvi_compose_sample.platform.DeviceStaticInfo
import com.suma.kmp_mvi_compose_sample.platform.currentDeviceMetrics
import com.suma.kmp_mvi_compose_sample.platform.currentDeviceStaticInfo

class DeviceInfoRepository {

    /** lazy 只算一次；失败不缓存（lazy 初始化异常不会被记住），下次调用会重试 */
    private val staticCache: DeviceStaticInfo by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        currentDeviceStaticInfo()
    }

    fun fetchStatic(): Result<DeviceStaticInfo> = runCatching { staticCache }

    /** 动态指标每次都取，避免旋转/窗口缩放后拿到旧值 */
    fun fetchMetrics(): Result<DeviceMetrics> = runCatching { currentDeviceMetrics() }
}