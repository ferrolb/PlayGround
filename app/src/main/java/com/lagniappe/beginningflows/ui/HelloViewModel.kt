package com.lagniappe.beginningflows.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

// You have a UserRepository that fetches user profiles from a remote
// API and caches locally. Write the ViewModel function that exposes the
// user profile to the UI using StateFlow, including error handling.
@HiltViewModel
class HelloViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    val userId: String = "123"
    val _viewState: StateFlow<HelloViewState> = MutableStateFlow(HelloViewState(UserProfile.Loading))
    val viewState: StateFlow<HelloViewState> = combine(
        _viewState,
        userRepository.getUserProfile(userId)
    ) { state, userProfile ->
        state.copy(userProfile = userProfile)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        HelloViewState(UserProfile.Loading)
    )
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindUserRepository(
        defaultUserRepository: DefaultUserRepository
    ): UserRepository
}

data class HelloViewState(
    val userProfile: UserProfile,
)

sealed interface UserProfile {
    data object Loading : UserProfile
    data class Success(
        val userId: String,
        val userName: String,
        val userEmail: String
    ) : UserProfile

    data class Error(val exception: Throwable) : UserProfile
}

interface UserRepository{
    fun getUserProfile(userId: String): Flow<UserProfile>
}

class DefaultUserRepository @Inject constructor(): UserRepository {
    override fun getUserProfile(userId: String): Flow<UserProfile> {
        // Simulate network call and caching logic
        return flow {
            try {
                // Simulate fetching from remote API
                val userProfile = fetchUserProfileFromApi(userId)
                // Simulate caching logic here (e.g., save to local database)
                emit(userProfile)
            } catch (e: Exception) {
                emit(UserProfile.Error(e))
            }
        }
    }

    private suspend fun fetchUserProfileFromApi(userId: String): UserProfile.Success {
        // Simulate network delay
        kotlinx.coroutines.delay(1000)
        // Simulate successful response
        return UserProfile.Success(
            userId = userId,
            userName = "John Doe",
            userEmail = "john@gmail.com"
        )
    }
}
