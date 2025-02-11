package com.example.emart.data.repository

import android.content.Context
import com.example.emart.data.api.ProductService
import com.example.emart.data.mapper.productMapper
import com.example.emart.data.model.ProductResponseItem
import com.example.emart.data.model.ProductResponseModel
import com.example.emart.di.network.apiutils.handleResponse

class ProductRepository(
    private val context: Context,
    private val serviceApi: ProductService,
) {

    suspend fun getProductItem(): ProductResponseModel {
        val handleResponse = handleResponse { serviceApi.getProductDetail() }
        return productMapper(context, handleResponse)
    }
}