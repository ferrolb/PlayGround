package com.lagniappe.beginningflows.data

import org.junit.Test

class NetworkResultTest {
    @Test
    fun `Given a Loading value returns Loading String`() {
        val result = NetworkResult.Loading
        val mapped = mapNetworkResultToString(result)
        assert(mapped == "Loading...")
    }
}