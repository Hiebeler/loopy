package com.hiebeler.loopy.ui.composables.home


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.usecases.GetForYouFeedUseCase
import com.hiebeler.loopy.domain.usecases.GetOwnUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getForYouFeedUseCase: GetForYouFeedUseCase
) : ViewModel() {


    var feedState by mutableStateOf(ForYouFeedState())

    init {
        getItemsFirstLoad(false)
    }

    private fun getItemsFirstLoad(refreshing: Boolean) {
        getForYouFeedUseCase().onEach { result ->
            feedState = when (result) {
                is Resource.Success -> {
                    ForYouFeedState(
                        feed = result.data ?: emptyList(),
                        error = "",
                        isLoading = false,
                        refreshing = false
                    )
                }

                is Resource.Error -> {
                    ForYouFeedState(
                        feed = feedState.feed,
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false,
                        refreshing = false
                    )
                }

                is Resource.Loading -> {
                    ForYouFeedState(
                        feed = feedState.feed, error = "", isLoading = true, refreshing = refreshing
                    )
                }
            }
        }.launchIn(viewModelScope)

    }

    fun loadMorePosts(maxId: String) {
        getForYouFeedUseCase(maxPostId = maxId).onEach { result ->
            feedState = when (result) {
                is Resource.Success -> {
                    ForYouFeedState(
                        feed = feedState.feed + (result.data ?: emptyList()),
                        error = "",
                        isLoading = false,
                        refreshing = false
                    )
                }

                is Resource.Error -> {
                    ForYouFeedState(
                        feed = feedState.feed,
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false,
                        refreshing = false
                    )
                }

                is Resource.Loading -> {
                    ForYouFeedState(
                        feed = feedState.feed, error = "", isLoading = true, refreshing = false
                    )
                }
            }
        }.launchIn(viewModelScope)

    }
}