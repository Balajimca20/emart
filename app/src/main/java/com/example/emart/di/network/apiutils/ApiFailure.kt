package com.example.emart.di.network.apiutils

import androidx.annotation.Keep

@Keep
data class ApiFailure(
    val msg: TextDto,
    val errorCode: String
) : Exception(msg.en) {
    companion object {

        fun parseErrorMessage(msg: TextDto): ApiFailure {
            return ApiFailure(
                msg,
                ""
            )
        }
    }
}

@Keep
data class TextDto(
    val en: String?,
    val ar: String?
)
