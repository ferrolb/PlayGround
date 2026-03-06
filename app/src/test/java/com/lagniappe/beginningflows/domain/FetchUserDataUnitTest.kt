package com.lagniappe.beginningflows.domain

import kotlinx.coroutines.test.runTest
import org.junit.Test

class FetchUserDataUnitTest {
    @Test
    fun `fetchUserData returns combined results when both APIs succeed`() = runTest {
        // Given
        val fetchUserData = FetchUserData()

        // When
        val result = fetchUserData.fetchUserData()

        // Then
        assert(result.dataField1 == "data1")
        assert(result.dataField2 == "data2")
    }

    @Test
    fun `fetchUserData handles API failures gracefully with default values`() = runTest {
        // Given
        val apiService = FetchUserData.APIService()

        // When - force an error in one API
        val result1 = apiService.fetchData1(forceError = true)
        val result2 = apiService.fetchData2()

        // Then - both should be failures
        assert(result1.isFailure)
        assert(result2.isSuccess)
    }

    @Test
    fun `fetchUserData combines results correctly`() = runTest {
        // Given
        val fetchUserData = FetchUserData()

        // When
        val result = fetchUserData.fetchUserData()

        // Then - verify both fields are present
        assert(result.dataField1.isNotEmpty())
        assert(result.dataField2.isNotEmpty())
        assert(result.dataField1 == "data1")
        assert(result.dataField2 == "data2")
    }

    @Test
    fun `fetchUserData combines with error in one API`() = runTest {
        // Given
        val fetchUserData = FetchUserData()

        // When
        val userData = fetchUserData.fetchUserData(forceError1 = true)

        // Then - verify both fields are present
        assert(userData.dataField1.isNotEmpty())
        assert(userData.dataField2.isNotEmpty())
        assert(userData.dataField1 == "defaultData1")
        assert(userData.dataField2 == "data2")
    }
}