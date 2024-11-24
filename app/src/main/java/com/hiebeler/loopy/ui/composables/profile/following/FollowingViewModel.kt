package com.hiebeler.loopy.ui.composables.profile.following


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.usecases.GetFollowersUseCase
import com.hiebeler.loopy.domain.usecases.GetFollowingUseCase
import com.hiebeler.loopy.domain.usecases.GetOwnUserUseCase
import com.hiebeler.loopy.ui.composables.own_profile.UserState
import com.hiebeler.loopy.ui.composables.profile.followers.FollowersState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(
    private val getFollowingUseCase: GetFollowingUseCase
) : ViewModel() {


    var followerState by mutableStateOf(FollowersState())

    fun loadData(accountId: String, refreshing: Boolean) {
        getFollowing(accountId, refreshing)
    }

    private fun getFollowing(accountId: String, refreshing: Boolean) {
        getFollowingUseCase(accountId).onEach { result ->
            followerState = when (result) {
                is Resource.Success -> {
                    FollowersState(followers = result.data)
                }

                is Resource.Error -> {
                    FollowersState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    FollowersState(
                        isLoading = true, followers = followerState.followers, refreshing = refreshing
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}