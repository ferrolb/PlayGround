package com.lagniappe.beginningflows.data

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Error(val exception: Throwable) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}

fun mapNetworkResultToString(result: NetworkResult<String>): String {
    return when (result) {
        is NetworkResult.Success -> "Success: ${result.data}"
        is NetworkResult.Error -> "Error: ${result.exception.message}"
        NetworkResult.Loading -> "Loading..."
    }
}