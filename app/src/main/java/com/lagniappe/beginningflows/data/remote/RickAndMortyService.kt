package com.lagniappe.beginningflows.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//data class ResponseItems<T>(
//    @field:Json(name = "results") val results: List<T>
//)
interface RickAndMortyService {

    // https://rickandmortyapi.com/api/character?page=1
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<CharacterResponse>
}

data class CharacterResponse(
    val info: Info,
    val results: List<CharacterType>
)

data class Info(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

data class CharacterType(
    val id: Int,
    val name: String
)