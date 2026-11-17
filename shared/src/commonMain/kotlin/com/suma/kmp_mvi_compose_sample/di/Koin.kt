package com.suma.kmp_mvi_compose_sample.di

import com.suma.kmp_mvi_compose_sample.data.StockRepository
import com.suma.kmp_mvi_compose_sample.network.api.EastMoneyApi
import com.suma.kmp_mvi_compose_sample.network.api.SinaApi
import com.suma.kmp_mvi_compose_sample.network.api.TencentApi
import com.suma.kmp_mvi_compose_sample.network.api.createEastMoneyApi
import com.suma.kmp_mvi_compose_sample.network.api.createSinaApi
import com.suma.kmp_mvi_compose_sample.network.api.createTencentApi
import com.suma.kmp_mvi_compose_sample.network.createHttpClient
import com.suma.kmp_mvi_compose_sample.network.createKtorfit
import io.ktor.client.HttpClient
import org.koin.dsl.module


    //single 数据库、Repository、HttpClient 等无界面状态的基础设施
    //viewModel ViewModelStoreOwner（Activity、Fragment 或导航目的地），界面业务类，随页面出栈自动清理
    //factory 轻量、有状态、短生命周期的对象，每次请求新建，用完即弃
    //scoped  自定义 Scope（可绑定到 Composable 或导航条目），需要页面级共享但也要随页面销毁的对象

val networkModule = module {

    single<HttpClient> { createHttpClient() }

    /**
     * 东方财富数据源
     */
    single<EastMoneyApi> {
        createKtorfit("https://push2.eastmoney.com/",get()).createEastMoneyApi()
    }

    /**
     * 新浪数据源
     */
    single<SinaApi> {
        createKtorfit("http://hq.sinajs.cn/",get()).createSinaApi()
    }

    /**
     * 腾讯数据源
     */
    single<TencentApi> {
        createKtorfit("http://hq.sinajs.cn/",get()).createTencentApi()
    }
}

val repositoryModule = module {

    single<StockRepository> {
        StockRepository(get(),get(),get())
    }

}


val appModule = module {
    //includes()让指定的module加到同一个全局容器
    includes(networkModule)
    includes(repositoryModule)
}