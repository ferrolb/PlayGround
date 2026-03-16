package com.lagniappe.beginningflows.ui.rickandmorty

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun RickAndMortyScreen(
    modifier: Modifier = Modifier,
    viewModel: RickAndMortyViewModel = hiltViewModel<RickAndMortyViewModel>()
) {
    val viewState = viewModel.getCharacters().collectAsLazyPagingItems()
    Column(modifier = modifier) {
        Text("Rick and Morty")
    }
}