package com.example.emart.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.emart.data.model.ProductResponseItem
import com.example.emart.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {

    var searchValue by mutableStateOf("")
        private set

    private var cacheProductItem = mutableListOf<ProductResponseItem>()

    private val _uiState = MutableStateFlow(ProductUiState(isLoading = true))
    val uiState: StateFlow<ProductUiState> = _uiState.stateIn(
        viewModelScope, SharingStarted.Eagerly, _uiState.value
    )

    init {
        refreshProductItems()
    }

    private fun refreshProductItems() {
        viewModelScope.launch {
            repository.getProductItem().let {
                cacheProductItem = it.productItem?.toMutableList() ?: mutableListOf()
                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        isRefreshing = false,
                        productItem = it.productItem ?: arrayListOf(),
                        errorMessage = if (it.status == false) it.message else ""
                    )
                }
            }
        }
    }

    fun searchList(query: String) {
        searchValue = query
        _uiState.update {
            it.copy(productItem = if (query.isEmpty()) cacheProductItem else cacheProductItem.filter { item ->
                item.title.contains(query, ignoreCase = true) || item.category.contains(
                    query,
                    ignoreCase = true
                )
            })
        }
    }

    fun onRefreshing(isLoading: Boolean) {
        searchValue=""
        _uiState.update {
            it.copy(
                isLoading = isLoading,
                isRefreshing = !isLoading
            )
        }
        viewModelScope.launch {
            refreshProductItems()
        }
    }

    data class ProductUiState(
        val isLoading: Boolean = false,
        val isRefreshing: Boolean = false,
        val productItem: List<ProductResponseItem> = emptyList(),
        val errorMessage: String? = ""
    )
}
