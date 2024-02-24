package com.example.jobstats.data

sealed class NetworkState<T> {

    data class Success<T>(val data: T) : NetworkState<T>()
    data class Failure<T>(val message: String) : NetworkState<T>()
    data object Loading : NetworkState<Nothing>()

}