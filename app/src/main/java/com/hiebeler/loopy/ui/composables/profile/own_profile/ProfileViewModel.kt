package com.hiebeler.loopy.ui.composables.profile.own_profile


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.usecases.GetOwnUserUseCase
import com.hiebeler.loopy.domain.usecases.GetPostsOfOwnUserUseCase
import com.hiebeler.loopy.ui.composables.post.PostsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getOwnUserUseCase: GetOwnUserUseCase,
    private val getPostsOfOwnUserUseCase: GetPostsOfOwnUserUseCase
) : ViewModel() {


    var ownProfileState by mutableStateOf(UserState())
    var postsState by mutableStateOf(PostsState())

    init {
        getOwnUser(false)
        loadPosts(false)
    }

    fun refresh() {
        getOwnUser(true)
        loadPosts(true)
    }

    private fun getOwnUser(refreshing: Boolean) {
        getOwnUserUseCase().onEach { result ->
            ownProfileState = when (result) {
                is Resource.Success -> {
                    UserState(user = result.data)
                }

                is Resource.Error -> {
                    UserState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    UserState(
                        isLoading = true, user = ownProfileState.user, refreshing = refreshing
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun loadPosts(refreshing: Boolean) {
        getPostsOfOwnUserUseCase().onEach { result ->
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
}