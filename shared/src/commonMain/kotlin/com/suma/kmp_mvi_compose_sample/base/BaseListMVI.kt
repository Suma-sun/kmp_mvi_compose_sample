package com.suma.kmp_mvi_compose_sample.base


interface BaseListIntent: BaseIntent{

    data object LoadMore:BaseListIntent

    data object Refresh:BaseListIntent

    data object Retry:BaseListIntent
}


interface PageType {
    fun first(): Int

    /**
     * 索引类型,初始为0
     */
    data object IndexType: PageType {
        override fun first(): Int = 0
    }

    /**
     * 页码类型,初始为1
     */
    data object NumberType: PageType {
        override fun first(): Int = 1
    }

//    data object Sensor: PageType {
//        interface SensorAble{
//            fun getSensor():String
//        }
//
//        override fun first(): Int = 0
//    }
}

/**
 * 列表viewModel基类
 */
abstract class BaseListViewModel<S:UiState,I: BaseListIntent>(initState: S) : BaseViewModel<S,I>(
    initState
) {
    protected open val pageType:PageType = PageType.IndexType

    /**
     * 首页
     */
    private val _firstPage = pageType.first()
    private var currPageNo = _firstPage
    protected open val pageSize = 20


    override fun onIntent(intent: I) {
        when(intent) {
            is BaseListIntent.Refresh -> onRefresh()
            is BaseListIntent.LoadMore -> onLoadMore()
            is BaseListIntent.Retry -> onRetry()
            else -> handleIntent(intent)
        }
    }

    private fun onRefresh() {
        currPageNo = _firstPage
        loadPage(currPageNo,pageSize)
    }

    private fun onLoadMore() {
        currPageNo++
        loadPage(currPageNo,pageSize)
    }

    private fun onRetry() {
        loadPage(currPageNo,pageSize)
    }

    /**
     * 分页加载
     */
    abstract fun loadPage(pageNo: Int, pageSize: Int)

}