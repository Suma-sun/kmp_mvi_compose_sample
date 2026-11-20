package com.suma.kmp_mvi_compose_sample.ui.main

import com.suma.kmp_mvi_compose_sample.base.BaseViewModel
import com.suma.kmp_mvi_compose_sample.base.EventDispatcher

class MainViewModel: BaseViewModel<MainState, MainIntent>(MainState()) {

    private val _flow = EventDispatcher<MainEvent> ()

    val flow = _flow.flow

    override fun handleIntent(intent: MainIntent) {
        when(intent) {
            is MainIntent.SelectedAction -> {
//                launch { _flow.emit(MainEvent.NavigateToScreen()) }
            }
        }
    }
}