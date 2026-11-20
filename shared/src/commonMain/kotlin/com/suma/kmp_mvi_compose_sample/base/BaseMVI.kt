package com.suma.kmp_mvi_compose_sample.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suma.kmp_mvi_compose_sample.base.BaseEvent.ShowToast
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


interface UiState

/**
 * MVI基础意图
 */
interface BaseIntent

/**
 * MVI基础动作
 */
interface BaseEvent {

    data class ShowToast(val content: String) : BaseEvent

}

/**
 * ViewModel基类</p>
 * 子类根据业务场景选择使用单个消费者[EventDispatcher]或多个消费者[EventBroadcaster]来处理数据流
 * @param S UI的状态
 * @param I 业务意图
 * @sample [SampleViewModel]
 */
abstract class BaseViewModel<S : UiState, I : BaseIntent>(initState: S) :
    ViewModel() {

    private val _state = MutableStateFlow<S>(initState)

    protected fun setState(reducer: S.() -> S) {
        _state.update { it.reducer() }
    }

    protected fun getState():S = _state.value

    protected fun launch(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }

    /**
     * UI状态FLOW
     */
    val uiState = _state.asStateFlow()

    /**
     * MVI意图唯一入口
     */
    open fun onIntent(intent: I) {
        handleIntent(intent)
    }

    protected abstract fun handleIntent(intent: I)
}

interface EventSink<E : BaseEvent> {
    val flow: Flow<E>

    suspend fun emit(event: E)

    fun onRelease()
}

/**
 *  单消费者：每条事件只被一个 collector 拿到，适合导航/Toast/弹窗</p>
 *  在ViewModel的super.onCleared()之前调用[EventDispatcher.onRelease]
 */
class EventDispatcher<E : BaseEvent>(
    capacity: Int = 64,//避免跨平台差异,使用JVM的固定值
) : EventSink<E> {
    private val _channel = Channel<E>(capacity)
    override val flow = _channel.receiveAsFlow()

    override suspend fun emit(event: E) {
        _channel.send(event)
    }

    override fun onRelease() {
        _channel.close()
    }
}

/**
 * 多消费者广播：所有 collector 都收到，适合跨页面通知
 * 在ViewModel的super.onCleared()之前调用[EventDispatcher.onRelease]
 * @param replay 重放数量
 * @param extraBufferCapacity 重放数量外的缓存大小
 * @param onBufferOverflow 缓存溢出时的策略,默认丢弃最早的数据
 */
class EventBroadcaster<E : BaseEvent>(
    replay: Int = 0,
    extraBufferCapacity: Int = 64,
    onBufferOverflow: BufferOverflow = BufferOverflow.DROP_OLDEST,
) : EventSink<E> {

    private val _flow = MutableSharedFlow<E>(replay, extraBufferCapacity, onBufferOverflow)

    override val flow = _flow.asSharedFlow()

    override suspend fun emit(event: E) {
        _flow.emit(event)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun onRelease() {
        _flow.resetReplayCache()
    }
}

data class SampleUiState(val name: String = "sample") : UiState
sealed interface SampleIntent : BaseIntent {
    data class SampleClick(val id: String) : SampleIntent

    data class Load(val type: String, val size: Int) : SampleIntent
}


class SampleViewModel : BaseViewModel<SampleUiState, SampleIntent>(SampleUiState()) {

    private val _event = EventDispatcher<BaseEvent>()

    val flow = _event.flow

    override fun handleIntent(intent: SampleIntent) {
        when (intent) {
            is SampleIntent.SampleClick -> {
                launch {
                    _event.emit(ShowToast(intent.id))
                }
            }

            is SampleIntent.Load -> {
                launch {
                    _event.emit(ShowToast("Load type=${intent.type} size=${intent.size}"))
                    setState {
                        copy(name = intent.type)
                    }
                }
            }
        }
    }


    override fun onCleared() {
        _event.onRelease()
        super.onCleared()
    }
}