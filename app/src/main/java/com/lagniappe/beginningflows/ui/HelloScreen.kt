package com.lagniappe.beginningflows.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun HelloScreen(
    modifier: Modifier = Modifier,
    // TODO: Use Hilt to inject the ViewModel
    viewModel: HelloViewModel = HelloViewModel(
        userId = "123",
        userRepository = UserRepository()
    )
) {
    val viewState = viewModel.viewState.collectAsState().value
    HelloScreen(
        modifier = modifier,
        viewState = viewState
    )
}

@Composable
fun HelloScreen(
    modifier: Modifier = Modifier,
    viewState: HelloViewState
) {
    Column(modifier = modifier) {
        when (val userProfile = viewState.userProfile) {
            is UserProfile.Loading -> Text("Loading user profile...")
            is UserProfile.Success -> Text("Hello, ${userProfile.userName}!")
            is UserProfile.Error -> Text("Error loading user profile: ${userProfile.exception.message}")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelloScreenLoadingPreview() {
    HelloScreen(viewState = HelloViewState(UserProfile.Loading))
}
@Preview(showBackground = true)
@Composable
fun HelloScreenSuccessPreview() {
    HelloScreen(viewState = HelloViewState(UserProfile.Success(
        userId = "123",
        userName = "John Doe",
        userEmail = "john@email.com"
    )))
}
@Preview(showBackground = true)
@Composable
fun HelloScreenErrorPreview() {
    HelloScreen(viewState = HelloViewState(UserProfile.Loading))
}
