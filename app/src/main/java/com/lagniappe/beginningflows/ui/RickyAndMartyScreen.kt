package com.lagniappe.beginningflows.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RickAndMortyScreen(
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text("Rick and Morty")
    }
}