package com.hiebeler.loopy.ui.composables.inbox


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Notification
import com.hiebeler.loopy.domain.model.State
import com.hiebeler.loopy.domain.model.Wrapper
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

    var inboxState by mutableStateOf(State<Wrapper<Notification>>(Wrapper()))

    init {
        getItemsFirstLoad(false)
    }

    fun refresh() {
        inboxState = inboxState.copy(isRefreshing = true)
        getItemsFirstLoad(true)
    }

    private fun getItemsFirstLoad(refreshing: Boolean) {
        getNotificationsUseCase().onEach { result ->
            inboxState = when (result) {
                is Resource.Success -> {
                    State(
                        data = result.data!!, error = "", isLoading = false, isRefreshing = false
                    )
                }

                is Resource.Error -> {
                    State(
                        data = inboxState.data,
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false,
                        isRefreshing = false
                    )
                }

                is Resource.Loading -> {
                    State(
                        data = inboxState.data,
                        error = "",
                        isLoading = true,
                        isRefreshing = refreshing
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadMoreNotifications() {
        if (inboxState.data.nextCursor == null) {
            return
        }
        getNotificationsUseCase(inboxState.data.nextCursor!!).onEach { result ->
            inboxState = when (result) {
                is Resource.Success -> {
                    State(
                        data = Wrapper(
                            inboxState.data.data + result.data!!.data,
                            result.data.previousCursor,
                            result.data.nextCursor
                        ), error = "", isLoading = false, isRefreshing = false
                    )
                }

                is Resource.Error -> {
                    inboxState.copy(
                        error = result.message ?: "An unexpected error occurred",
                        isLoading = false,
                        isRefreshing = false
                    )
                }

                is Resource.Loading -> {
                    inboxState.copy(
                        error = "", isLoading = true, isRefreshing = false
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}