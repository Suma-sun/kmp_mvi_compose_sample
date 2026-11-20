package com.suma.kmp_mvi_compose_sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.suma.kmp_mvi_compose_sample.di.initKoin

fun main() = application {
    initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP Nav"
    ) {
        App()
    }
}