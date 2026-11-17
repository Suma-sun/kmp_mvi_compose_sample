//package com.suma.stocklist.di
//
//import com.suma.kmp_mvi_compose_sample.network.createHttpClient
//import com.suma.stocklist.StockListViewModel
////import com.suma.stocklist.network.api.createEastMoneyApi
////import com.suma.stocklist.network.api.createSinaApi
////import com.suma.stocklist.network.api.createTencentApi
//import com.suma.kmp_mvi_compose_sample.data.StockRepository
//import io.ktor.client.HttpClient
//import org.koin.core.module.dsl.viewModel
//import org.koin.dsl.module
//
////顶层属性，module{}相当于一个容器，single{}实际上只是注册了一个方法进去，
//// 在没有使用时是不会调用Lambda，当调用了get<T>时才会执行对应的lambda创建对象并缓存
//val stockListModule = module {
//
//    //single 数据库、Repository、HttpClient 等无界面状态的基础设施
//    //viewModel ViewModelStoreOwner（Activity、Fragment 或导航目的地），界面业务类，随页面出栈自动清理
//    //factory 轻量、有状态、短生命周期的对象，每次请求新建，用完即弃
//    //scoped  自定义 Scope（可绑定到 Composable 或导航条目），需要页面级共享但也要随页面销毁的对象
//
////    single<HttpClient> { createHttpClient() }
//
//    // 东方财富的Ktorfit实例（独立baseUrl）
//    single<EastMoneyApi> {
//        createKtorfit("https://push2.eastmoney.com/",get()).createEastMoneyApi()
//    }
//    // 腾讯
//    single<TencentApi> {
//        createKtorfit("https://qt.gtimg.cn/",get()).createTencentApi()
//    }
//    // 新浪
//    single<SinaApi> {
//        createKtorfit("http://hq.sinajs.cn/",get()).createSinaApi()
//    }
//    single<StockRepository> {
//        StockRepository(get(), get(), get())
//    }
//    viewModel { StockListViewModel(get()) }
//}