package com.hiebeler.loopy.ui.composables.inbox


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.FeedWrapper
import com.hiebeler.loopy.domain.usecases.GetForYouFeedUseCase
import com.hiebeler.loopy.domain.usecases.GetNotificationsUseCase
import com.hiebeler.loopy.domain.usecases.GetOwnUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class InboxViewModel @Inject constructor(
    private val getNotificationsUseCase: GetNotificationsUseCase
) : ViewModel() {

    var inboxState by mutableStateOf(InboxState())

    init {
        getItemsFirstLoad(false)
    }

    private fun getItemsFirstLoad(refreshing: Boolean) {
        getNotificationsUseCase().onEach { result ->
            inboxState = when (result) {
                is Resource.Success -> {
                    InboxState(
                        notifications = result.data?.data ?: emptyList(),
                        nextCursor = result.data?.nextCursor ?: "",
                        previousCursor = result.data?.nextCursor ?: "",
                        error = "",
                        isLoading = false,
                        refreshing = false
                    )
                }

                is Resource.Error -> {
                    InboxState(
                        notifications = inboxState.notifications,
                        nextCursor = inboxState.nextCursor,
                        previousCursor = inboxState.previousCursor,
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false,
                        refreshing = false
                    )
                }

                is Resource.Loading -> {
                    InboxState(
                        notifications = inboxState.notifications,
                        nextCursor = inboxState.nextCursor,
                        previousCursor = inboxState.previousCursor,
                        error = "",
                        isLoading = true,
                        refreshing = refreshing
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadMorePosts(nextCursor: String) {
//        getForYouFeedUseCase(maxPostId = nextCursor).onEach { result ->
//            inboxState = when (result) {
//                is Resource.Success -> {
//                    InboxState(
//                        inbox = FeedWrapper(data = inboxState.inbox!!.data + result.data!!.data, links = result.data.links, meta = result.data.meta),
//                        error = "",
//                        isLoading = false,
//                        refreshing = false
//                    )
//                }
//
//                is Resource.Error -> {
//                    InboxState(
//                        inbox = inboxState.inbox,
//                        error = result.message ?: "An unexpected error occurred",
//                        isLoading = false,
//                        refreshing = false
//                    )
//                }
//
//                is Resource.Loading -> {
//                    InboxState(
//                        inbox = inboxState.inbox, error = "", isLoading = true, refreshing = false
//                    )
//                }
//            }
//        }.launchIn(viewModelScope)
    }
}