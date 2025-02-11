package com.example.emart.di.network.apiutils

import android.accounts.NetworkErrorException
import android.text.TextUtils
import android.util.Log
import androidx.annotation.Keep
import com.example.emart.R
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> handleResponse(api: suspend () -> retrofit2.Response<T?>): Response<T?> {
    try {
        val response = api()
        return if (response.isSuccessful) {
            Response.Success(response.body())
        } else {
                val message = getErrorMessageFromGenericResponse(response)
                if (!TextUtils.isEmpty(message)) {
                    Response.Failure(ApiFailure.parseErrorMessage(TextDto(message, message)))
                } else {
                    Response.NoNetwork(R.string.server_error)
                }

        }
    } catch (e: Exception) {
        Log.d("TAG", "handleResponse: ${e.printStackTrace()}", e)
        return when (e) {
            is NetworkErrorException -> {
                Response.NoNetwork(R.string.no_network_connection)
            }

            is SocketTimeoutException -> {
                Response.NoNetwork(R.string.something_times)
            }

            else -> {
                Response.NoNetwork(R.string.something_went_wrong)
            }
        }
    }
}

private fun <T> getErrorMessageFromGenericResponse(response: retrofit2.Response<T?>): String? {
    var errorMessage: String? = null
    try {
        val apiResponse: ApiResponse? = Gson().fromJson(response.errorBody()?.string())
        if (apiResponse != null && TextUtils.isEmpty(apiResponse.message)) {
            return apiResponse.message
        }
    } catch (e: java.lang.Exception) {
        Log.e("TAG", "getErrorMessageFromGenericResponse: ", e)
    }
    try {
        val errorBody = response.errorBody()
        if (errorBody != null) {
            val json = errorBody.string()
            val errorParser: CommonApiError? = Gson().fromJson(json)
            val data = errorParser?.data
            if (!data.isNullOrEmpty()) {
                val get = data[0]
                if (get.isNotEmpty()) {
                    if (!TextUtils.isEmpty(get[0])) {
                        errorMessage = get[0]
                    }
                }
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        return errorMessage
    }
}

@Keep
data class ApiResponse(
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: Any?,
)

@Keep
data class CommonApiError(
    @SerializedName("status_code")
    val statusCode: Int?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("data")
    val data: List<List<String?>>,
)