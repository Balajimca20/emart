package com.example.emart.data.model


import androidx.annotation.Keep


@Keep
data class ProductResponseModel(
    val statusCode: Int?,
    val status: Boolean?,
    val message: String?,
    val productItem: List<ProductResponseItem>?
)

@Keep
data class ProductResponseItem(
    val category: String,
    val description: String,
    val id: Int,
    val image: String,
    val price: Double,
    val rating: Rating,
    val title: String
)

@Keep
data class Rating(
    val count: Int,
    val rate: Double
)
