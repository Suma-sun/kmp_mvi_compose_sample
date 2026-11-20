package com.suma.kmp_mvi_compose_sample

import android.app.Application
import com.suma.kmp_mvi_compose_sample.di.initKoin
import org.koin.android.ext.koin.androidContext

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@MainApplication)
        }
    }
}