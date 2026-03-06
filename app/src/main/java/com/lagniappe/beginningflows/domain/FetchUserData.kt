package com.lagniappe.beginningflows.domain

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

class FetchUserData {
    // Write a function that fetches user data from two APIs concurrently, then combines the results.
    // Make sure exceptions in one API do not cancel the other.
    suspend fun fetchUserData(forceError1: Boolean = false, forceError2: Boolean = false): UserData = coroutineScope {

        val apiService = APIService()
//
//        val userDeferred = async { fetchUserFromApi() }
//        val profileDeferred = async { fetchProfileFromApi() }
//
//        val userResult = runCatching { userDeferred.await() }
//        val profileResult = runCatching { profileDeferred.await() }

        val data1Deferred = async {apiService.fetchData1(forceError = forceError1)}
        val data2Deferred = async {apiService.fetchData2(forceError = forceError2)}

        val data1 = runCatching { data1Deferred.await().getOrThrow() }
        val data2 = runCatching { data2Deferred.await().getOrThrow() }

        UserData(
            dataField1 = data1.getOrElse { "defaultData1" },
            dataField2 = data2.getOrElse { "defaultData2" }
        )
    }

    class APIService {
        suspend fun fetchData1(forceError: Boolean = false): Result<String> {
            delay(500)
            return if (forceError) {
                Result.failure(Exception("Error in fetching data1"))
            } else {
                Result.success("data1")
            }
        }

        suspend fun fetchData2(forceError: Boolean = false): Result<String> {
            delay(300)
            return if (forceError) {
                Result.failure(Exception("Error in fetching data2"))
            } else {
                Result.success("data2")
            }
        }
    }
}

data class UserData (
    val dataField1 : String,
    val dataField2: String
)
