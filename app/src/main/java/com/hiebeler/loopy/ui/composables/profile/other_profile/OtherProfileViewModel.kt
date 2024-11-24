package com.hiebeler.loopy.ui.composables.own_profile


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.usecases.FollowUserUseCase
import com.hiebeler.loopy.domain.usecases.GetPostsOfUserUseCase
import com.hiebeler.loopy.domain.usecases.GetUserUseCase
import com.hiebeler.loopy.domain.usecases.UnfollowUserUseCase
import com.hiebeler.loopy.ui.composables.post.PostsState
import com.hiebeler.loopy.ui.composables.profile.own_profile.UserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OtherProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getPostsOfUserUseCase: GetPostsOfUserUseCase,
    private val followAccountUseCase: FollowUserUseCase,
    private val unfollowAccountUseCase: UnfollowUserUseCase
) : ViewModel() {


    var profileState by mutableStateOf(UserState())
    var postsState by mutableStateOf(PostsState())
    var followIsLoading by mutableStateOf(false)

    fun loadData(userId: String, refreshing: Boolean) {
        loadUser(userId, refreshing)
        loadPostsOfUser(userId, refreshing)
    }

    fun refresh() {
        loadUser(profileState.user!!.id, true)
        loadPostsOfUser(profileState.user!!.id, true)
    }

    private fun loadUser(userId: String, refreshing: Boolean) {
        getUserUseCase(userId).onEach { result ->
            profileState = when (result) {
                is Resource.Success -> {
                    UserState(user = result.data)
                }

                is Resource.Error -> {
                    UserState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    UserState(
                        isLoading = true, user = profileState.user, refreshing = refreshing
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadPostsOfUser(userId: String, refreshing: Boolean) {
        getPostsOfUserUseCase(userId).onEach { result ->
            postsState = when (result) {
                is Resource.Success -> {
                    PostsState(feed = result.data)
                }

                is Resource.Error -> {
                    PostsState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    PostsState(
                        isLoading = true, feed = postsState.feed, refreshing = refreshing
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun followAccount(userId: String) {
        followIsLoading = true
        followAccountUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val updatedUser = profileState.user?.copy(following = result.data!!.following)
                    profileState = profileState.copy(user = updatedUser)

                    followIsLoading = false
                }

                is Resource.Error -> {
                    followIsLoading = false
                }

                is Resource.Loading -> {
                    followIsLoading = true
                }
            }
        }.launchIn(viewModelScope)
    }

    fun unfollowAccount(userId: String) {
        followIsLoading = true
        unfollowAccountUseCase(userId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    val updatedUser = profileState.user?.copy(following = result.data!!.following)
                    profileState = profileState.copy(user = updatedUser)

                    followIsLoading = false
                }

                is Resource.Error -> {
                    followIsLoading = false
                }

                is Resource.Loading -> {
                    followIsLoading = true
                }
            }
        }.launchIn(viewModelScope)
    }

}