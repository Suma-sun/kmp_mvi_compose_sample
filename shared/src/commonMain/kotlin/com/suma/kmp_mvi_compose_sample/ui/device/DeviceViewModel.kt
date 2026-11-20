package com.suma.kmp_mvi_compose_sample.ui.device

import androidx.lifecycle.viewModelScope
import com.suma.kmp_mvi_compose_sample.base.BaseEvent
import com.suma.kmp_mvi_compose_sample.base.BaseViewModel
import com.suma.kmp_mvi_compose_sample.base.EventDispatcher
import com.suma.kmp_mvi_compose_sample.data.DeviceInfoRepository
import kotlinx.coroutines.launch

class DeviceViewModel(private val repository: DeviceInfoRepository): BaseViewModel<DeviceState, DeviceIntent>(
    DeviceState()
) {

    /** 页面级防重入：同一 ViewModel 生命周期内不重复捞 */
    private var staticLoaded = false

    private val _flow = EventDispatcher<BaseEvent>()

    init {
        onIntent(DeviceIntent.Load)
    }

    override fun handleIntent(intent: DeviceIntent) {
        when(intent){
            DeviceIntent.CopyModel -> copyModel()
            DeviceIntent.Load -> load()
            DeviceIntent.RefreshMetrics -> refreshMetrics()
            DeviceIntent.Retry -> load()
        }
    }



    private fun load() = viewModelScope.launch {
        val state = getState()
        if (staticLoaded && state.static != null) return@launch   // 命中页面级缓存
        setState { copy(isLoading=true, error = null) }
        repository.fetchStatic()
            .onSuccess { info ->
                staticLoaded = true
                setState { copy(isLoading = false, static = info) }
            }
            .onFailure { e ->
                // TODO: 多语言 
                setState { copy(isLoading = false, error = e.message ?: "未知错误") }
                _flow.emit(BaseEvent.ShowToast("加载失败：${e.message}"))
            }
        refreshMetrics()
    }

    private fun copyModel() = viewModelScope.launch {
        val model = getState().static?.deviceModel
        // TODO: 多语言
        _flow.emit(BaseEvent.ShowToast(if (model != null) "已复制：$model" else "暂无数据"))
    }

    private fun refreshMetrics() = viewModelScope.launch {
        repository.fetchMetrics().onSuccess { m ->
            setState { copy(metrics = m) }
        }
    }

}