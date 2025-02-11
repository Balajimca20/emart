package com.example.emart.di


import com.example.emart.data.repository.ProductRepository
import com.example.emart.di.network.ApiProvider
import com.example.emart.ui.viewmodel.ProductViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


object AppModule {
    fun appModules() = viewModelModules + repoModules + commonModules

    private val commonModules = module {
        single { ApiProvider.client }

    }

    private val repoModules = module {
        single { ProductRepository(get(),get()) }
    }

    private val viewModelModules = module {
        viewModel { ProductViewModel(get()) }
    }

}