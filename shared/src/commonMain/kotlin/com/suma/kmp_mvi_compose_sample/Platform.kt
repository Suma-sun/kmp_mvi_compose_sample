package com.suma.kmp_mvi_compose_sample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform