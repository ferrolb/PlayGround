package com.lagniappe.beginningflows.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.lagniappe.beginningflows.data.local.NETWORK_PAGE_SIZE
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class RickAndMortyPagingSource(
    private val service: RickAndMortyService
) : PagingSource<Int, CharacterType>() {
    override fun getRefreshKey(state: PagingState<Int, CharacterType>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterType> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getCharacters(
                page = pageIndex
            )
            val characters = response.body()?.results ?: emptyList()
            val nextKey =
                if (characters.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = characters,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}