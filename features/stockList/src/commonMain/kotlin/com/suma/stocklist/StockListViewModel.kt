package com.suma.stocklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suma.kmp_mvi_compose_sample.domain.Stock
import com.suma.kmp_mvi_compose_sample.data.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StockListViewModel(private val repository: StockRepository) : ViewModel() {
    private val _state = MutableStateFlow(StockListState())
    val state: StateFlow<StockListState> = _state.asStateFlow()

    fun onIntent(intent: StockListIntent) {
        when (intent) {
            StockListIntent.Load -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            repository.getStockList()
                .onSuccess { list ->
                    _state.update { it.copy(isLoading = false, stocks = list) }
                }
                .onFailure { e ->
                    _state.update { it.copy(isLoading = false, error = e.message) }
                }
        }
    }
}

data class StockListState(
    val isLoading: Boolean = false,
    val stocks: List<Stock> = emptyList(),
    val error: String? = null
)

sealed interface StockListIntent {
    data object Load : StockListIntent
}