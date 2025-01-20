package com.hiebeler.loopy.ui.composables.post.single_post_page

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.State
import com.hiebeler.loopy.domain.usecases.GetPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SinglePostViewModel @Inject constructor(
    private val getPostUseCase: GetPostUseCase
) : ViewModel() {


    var postState by mutableStateOf(State<Post?>(null))

    fun getPost(postId: String) {
        getPostUseCase(postId).onEach { result ->
            postState = when (result) {
                is Resource.Success -> {
                    State(data = result.data)
                }

                is Resource.Error -> {
                    State<Post?>(
                        error = result.message ?: "An unexpected error occurred", data = null
                    )
                }

                is Resource.Loading -> {
                    State<Post?>(isLoading = true, data = postState.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}