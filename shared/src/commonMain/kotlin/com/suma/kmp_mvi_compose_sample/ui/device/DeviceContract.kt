package com.suma.kmp_mvi_compose_sample.ui.device

import com.suma.kmp_mvi_compose_sample.base.BaseIntent
import com.suma.kmp_mvi_compose_sample.base.UiState
import com.suma.kmp_mvi_compose_sample.platform.DeviceMetrics
import com.suma.kmp_mvi_compose_sample.platform.DeviceStaticInfo

/**
 * 设备信息页的UI状态
 */
data class DeviceState(
    val isLoading: Boolean = false,
    val static: DeviceStaticInfo? = null,
    val metrics: DeviceMetrics? = null,
    val error: String? = null,
): UiState

/**
 * 设备信息页的意图
 */
sealed interface DeviceIntent: BaseIntent {
    data object Load : DeviceIntent
    data object Retry : DeviceIntent
    data object RefreshMetrics : DeviceIntent
    data object CopyModel : DeviceIntent
}