package com.suma.kmp_mvi_compose_sample.ui.main

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

class MainScreen: Screen {


    @Composable
    override fun Content() {

        // 拿到最近的 Navigator（类似 LocalContext），用于 push 详情页
        val navigator = LocalNavigator.currentOrThrow


    }

}