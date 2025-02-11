package com.example.emart.di.network.apiutils


sealed class Response<T> {
    class Success<T>(val data: T) : Response<T>() {
    }

    class Failure<T>(val error: ApiFailure) : Response<T>() {
    }

    class NoNetwork<T>(val messageId: Int) : Response<T>() {
    }
}
