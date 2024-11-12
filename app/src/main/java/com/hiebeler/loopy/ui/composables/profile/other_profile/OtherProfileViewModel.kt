package com.hiebeler.loopy.ui.composables.own_profile


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.usecases.GetOwnUserUseCase
import com.hiebeler.loopy.domain.usecases.GetPostsOfUserUseCase
import com.hiebeler.loopy.domain.usecases.GetUserUseCase
import com.hiebeler.loopy.ui.composables.post.PostsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OtherProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val getPostsOfUserUseCase: GetPostsOfUserUseCase
) : ViewModel() {


    var profileState by mutableStateOf(UserState())
    var postsState by mutableStateOf(PostsState())


    fun loadData(userId: String, refreshing: Boolean) {
        loadUser(userId, refreshing)
        loadPostsOfUser(userId, refreshing)
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
                    PostsState(posts = result.data ?: emptyList())
                }

                is Resource.Error -> {
                    PostsState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    PostsState(
                        isLoading = true, posts = postsState.posts, refreshing = refreshing
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}