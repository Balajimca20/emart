package com.example.emart.ui.view.productscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.emart.R
import com.example.emart.data.model.ProductResponseItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.util.Currency

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    isLoading: Boolean,
    productItem: List<ProductResponseItem>?,
    searchValue: String,
    onSearchValueChanged: (String) -> Unit,
    onRefreshing: (Boolean) -> Unit,
    isRefreshing: Boolean,
    errorMessage: String?,
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val rupeeSymbol = Currency.getInstance("INR").symbol
    var clearText by remember { mutableStateOf(false) }
    val isGridView = remember { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val state = rememberSwipeRefreshState(isRefreshing)

    LaunchedEffect(errorMessage) {
        if (!errorMessage.isNullOrBlank()) snackBarHostState.showSnackbar(errorMessage)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.layout_bg))
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackBarHostState) },
        topBar = {
            Column(modifier = Modifier.fillMaxWidth()) {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.app_name),
                            color = colorResource(id = R.color.text_color)
                        )
                    },
                    scrollBehavior = scrollBehavior,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorResource(id = R.color.white),
                        titleContentColor = colorResource(id = R.color.text_color)
                    )
                )
                SearchBar(
                    value = searchValue,
                    onSearchValueChanged = onSearchValueChanged,
                    clearText = clearText,
                    onDismissClearText = { clearText = it },
                    onDismiss = { onSearchValueChanged("") },
                    onClickAction = { isGridView.value = !isGridView.value },
                    isGridView = isGridView.value
                )
                if (isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                SwipeRefresh(
                    state = state,
                    onRefresh = { onRefreshing(false) },
                    indicator = { refreshing, refreshTrigger ->
                        SwipeRefreshIndicator(
                            state = refreshing,
                            refreshTriggerDistance = refreshTrigger,
                            scale = true,
                        )
                    }
                ) {
                    if (!isLoading && productItem?.isEmpty() == true) {
                        EmptyScreen(onClickReload = { onRefreshing(true) })
                    }
                    LazyVerticalGrid(columns = GridCells.Fixed(if (isGridView.value) 1 else 2)) {
                        items(productItem.orEmpty()) { item ->
                            ProductItem(
                                item,
                                rupeeSymbol,
                                isGridView = isGridView.value
                            )
                        }
                    }
                }
            }
        }
    )
}



