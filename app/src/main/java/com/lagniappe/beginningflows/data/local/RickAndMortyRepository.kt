package com.lagniappe.beginningflows.data.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.lagniappe.beginningflows.data.remote.RickAndMortyPagingSource
import com.lagniappe.beginningflows.data.remote.RickAndMortyService
import javax.inject.Inject

const val NETWORK_PAGE_SIZE = 20

class RickAndMortyRepository @Inject constructor(
    private val rickAndMortyService: RickAndMortyService
) {
    fun getCharacters() = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
        pagingSourceFactory = { RickAndMortyPagingSource(rickAndMortyService) }
    ).flow
}