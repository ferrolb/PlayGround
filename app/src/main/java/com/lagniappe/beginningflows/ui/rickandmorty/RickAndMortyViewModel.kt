package com.lagniappe.beginningflows.ui.rickandmorty

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.lagniappe.beginningflows.data.local.RickAndMortyRepository
import com.lagniappe.beginningflows.data.remote.CharacterType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RickAndMortyViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {
    // TODO: move to domain use case
    fun getCharacters(): Flow<PagingData<CharacterType>> = repository.getCharacters()
}