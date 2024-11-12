package com.hiebeler.loopy.ui.composables.own_profile


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.usecases.GetOwnUserUseCase
import com.hiebeler.loopy.domain.usecases.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class OtherProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {


    var profileState by mutableStateOf(UserState())


    fun loadData(userId: String, refreshing: Boolean) {
        loadUser(userId, refreshing)
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
}