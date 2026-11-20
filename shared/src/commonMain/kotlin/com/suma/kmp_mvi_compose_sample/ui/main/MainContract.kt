package com.suma.kmp_mvi_compose_sample.ui.main

import com.suma.kmp_mvi_compose_sample.base.BaseEvent
import com.suma.kmp_mvi_compose_sample.base.BaseIntent
import com.suma.kmp_mvi_compose_sample.base.UiState


data class MainState(
    val selected: Int = 0,
    val isLoading: Boolean = false
) : UiState

sealed interface MainIntent : BaseIntent {

    /**
     * 选中tab意图
     */
    data class SelectedAction(val index: Int) : MainIntent

}

sealed interface MainEvent : BaseEvent {

    data class NavigateToScreen(val id: String) : MainEvent
}



