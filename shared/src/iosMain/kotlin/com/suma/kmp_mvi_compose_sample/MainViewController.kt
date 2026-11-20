package com.suma.kmp_mvi_compose_sample

import androidx.compose.ui.window.ComposeUIViewController
import com.suma.kmp_mvi_compose_sample.di.initKoin
import platform.UIKit.UIViewController

fun MainViewController() : UIViewController {
    initKoin()
   return  ComposeUIViewController { App() }
}