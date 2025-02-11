package com.example.emart.data.mapper

import android.content.Context
import com.example.emart.data.model.ProductResponseItem
import com.example.emart.data.model.ProductResponseModel
import com.example.emart.di.network.apiutils.Response


fun productMapper(
    context: Context,
    response: Response<List<ProductResponseItem>?>,
): ProductResponseModel {
    return when (response) {
        is Response.Failure -> ProductResponseModel(
            statusCode = 500,
            status = false,
            message = response.error.msg.en,
            productItem = arrayListOf(),
        )

        is Response.NoNetwork -> ProductResponseModel(
            statusCode = 500,
            status = false,
            message = context.getString(response.messageId),
            productItem = arrayListOf(),
        )

        is Response.Success -> ProductResponseModel(
            statusCode = 200,
            status = true,
            message = "Data retrieved",
            productItem = response.data,
        )
    }
}