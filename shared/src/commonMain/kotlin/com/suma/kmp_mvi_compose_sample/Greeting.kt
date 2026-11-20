package com.suma.kmp_mvi_compose_sample

import com.suma.kmp_mvi_compose_sample.platform.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return sayHello(platform.name)
    }
}