package com.hiebeler.loopy.ui.composables.home


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.State
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.domain.usecases.GetForYouFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getForYouFeedUseCase: GetForYouFeedUseCase
) : ViewModel() {

    var feedState by mutableStateOf(State<Wrapper<Post>>(Wrapper(), isLoading = true))

    init {
        getItemsFirstLoad(false)
    }

    fun refresh() {
        getItemsFirstLoad(true)
    }

    private fun getItemsFirstLoad(refreshing: Boolean) {
        getForYouFeedUseCase().onEach { result ->
            feedState = when (result) {
                is Resource.Success -> {
                    State(
                        data = result.data!!, error = "", isLoading = false, isRefreshing = false
                    )
                }

                is Resource.Error -> {
                    State(
                        data = feedState.data,
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false,
                        isRefreshing = false
                    )
                }

                is Resource.Loading -> {
                    State(
                        data = feedState.data,
                        error = "",
                        isLoading = true,
                        isRefreshing = refreshing
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun activePostChanged(index: Int) {
        if (!feedState.isLoading && index > feedState.data.data.size - 3) {
            feedState.data.nextCursor?.let { loadMorePosts(it) }
        }
    }

    private fun loadMorePosts(nextCursor: String) {
        if (!feedState.isLoading) {
            getForYouFeedUseCase(maxPostId = nextCursor).onEach { result ->
                feedState = when (result) {
                    is Resource.Success -> {
                        State(
                            data = Wrapper(
                                data = feedState.data.data + result.data!!.data,
                                previousCursor = result.data.previousCursor,
                                nextCursor = result.data.nextCursor
                            ), error = "", isLoading = false, isRefreshing = false
                        )
                    }

                    is Resource.Error -> {
                        State(
                            data = feedState.data,
                            error = result.message ?: "An unexpected error occurred",
                            isLoading = false,
                            isRefreshing = false
                        )
                    }

                    is Resource.Loading -> {
                        State(
                            data = feedState.data,
                            error = "",
                            isLoading = true,
                            isRefreshing = false
                        )
                    }
                }
            }.launchIn(viewModelScope)
        }

    }
}