package com.hiebeler.loopy.ui.composables.profile.following


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.State
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.domain.usecases.GetFollowingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val getFollowingUseCase: GetFollowingUseCase
) : ViewModel() {


    var followerState by mutableStateOf(State<Wrapper<Account>>(Wrapper()))

    fun loadData(accountId: String, refreshing: Boolean) {
        getFollowing(accountId, refreshing)
    }

    private fun getFollowing(accountId: String, refreshing: Boolean) {
        getFollowingUseCase(accountId).onEach { result ->
            followerState = when (result) {
                is Resource.Success -> {
                    State(data = result.data!!)
                }

                is Resource.Error -> {
                    State(
                        error = result.message ?: "An unexpected error occurred", data = Wrapper()
                    )
                }

                is Resource.Loading -> {
                    State(
                        isLoading = true, data = followerState.data, isRefreshing = refreshing
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}