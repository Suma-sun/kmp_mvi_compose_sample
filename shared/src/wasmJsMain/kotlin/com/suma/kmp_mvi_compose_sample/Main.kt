package com.suma.kmp_mvi_compose_sample

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.suma.kmp_mvi_compose_sample.di.initKoin
import org.jetbrains.skiko.wasm.onWasmReady

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    initKoin()
    onWasmReady {
        ComposeViewport {
            App()
        }
    }

    ComposeViewport {
        App()
    }
}