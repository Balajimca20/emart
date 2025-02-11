package com.example.emart.data.api

import com.example.emart.data.model.ProductResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    @GET("products")
    suspend fun getProductDetail(
    ): Response<List<ProductResponseItem>?>
}