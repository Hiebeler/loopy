package com.hiebeler.loopy.ui.composables.profile.own_profile


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.State
import com.hiebeler.loopy.domain.model.Wrapper
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


    var ownProfileState by mutableStateOf(State<Account?>(null))
    var postsState by mutableStateOf(PostsState())

    init {
        getOwnUser(false)
        loadPosts(false)
    }

    fun refresh(refreshing: Boolean = true) {
        getOwnUser(refreshing)
        loadPosts(refreshing)
    }

    fun updateAccountSwitch() {
        refresh(false)
    }

    private fun getOwnUser(refreshing: Boolean) {
        getOwnUserUseCase().onEach { result ->
            ownProfileState = when (result) {
                is Resource.Success -> {
                    State(data = result.data)
                }

                is Resource.Error -> {
                    State(
                        error = result.message ?: "An unexpected error occurred",
                        data = ownProfileState.data
                    )
                }

                is Resource.Loading -> {
                    State(
                        isLoading = true, data = ownProfileState.data, isRefreshing = refreshing
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

    fun loadMorePosts() {
        postsState.feed?.nextCursor?.let {
            getPostsOfOwnUserUseCase(it).onEach { result ->
                postsState = when (result) {
                    is Resource.Success -> {
                        postsState.copy(
                            isLoading = false, feed = Wrapper<Post>(
                                nextCursor = result.data!!.nextCursor,
                                previousCursor = result.data.previousCursor,
                                data = postsState.feed!!.data + result.data.data
                            )
                        )
                    }

                    is Resource.Error -> {
                        postsState.copy(error = "an error occurred!")
                    }

                    is Resource.Loading -> {
                        postsState.copy()
                    }
                }
            }.launchIn(viewModelScope)
        }
    }
}