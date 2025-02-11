package com.example.emart.ui.view.productscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.emart.ui.viewmodel.ProductViewModel


@Composable
fun ProductScreenRoute(viewModel: ProductViewModel) {

    val uiState by viewModel.uiState.collectAsState()
    ProductScreen(
        isLoading = uiState.isLoading,
        productItem = uiState.productItem,
        searchValue = viewModel.searchValue,
        onSearchValueChanged = { query -> viewModel.searchList(query) },
        onRefreshing = { viewModel.onRefreshing(it) },
        isRefreshing = uiState.isRefreshing,
        errorMessage = uiState.errorMessage,
    )
}