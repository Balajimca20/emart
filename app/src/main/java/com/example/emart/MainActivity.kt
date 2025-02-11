package com.example.emart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.emart.ui.theme.EmartTheme
import com.example.emart.ui.view.productscreen.ProductScreenRoute
import com.example.emart.ui.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: ProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EmartTheme {
                ProductScreenRoute(viewModel)
            }
        }
    }
}
