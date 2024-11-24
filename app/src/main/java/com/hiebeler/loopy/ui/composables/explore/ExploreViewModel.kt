package com.hiebeler.loopy.ui.composables.explore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.domain.usecases.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel() {
    var searchState by mutableStateOf(SearchState())

    fun textInputChange(text: String) {
        searchDebounced(text)
    }

    private var searchJob: Job? = null

    private fun searchDebounced(searchText: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            if (searchText.isNotBlank()) {
                getSearchResults(searchText)
            }
        }
    }

    private fun getSearchResults(text: String) {
        searchUseCase(text).onEach { result ->
            searchState = when (result) {
                is Resource.Success -> {
                    SearchState(searchResult = result.data)
                }

                is Resource.Error -> {
                    SearchState(error = result.message ?: "An unexpected error occurred")
                }

                is Resource.Loading -> {
                    SearchState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun loadMoreSearchResults(query: String) {
        if (searchState.searchResult == null || searchState.searchResult!!.nextCursor == null) return
        searchUseCase(query, searchState.searchResult!!.nextCursor!!).onEach { result ->
            searchState = when (result) {
                is Resource.Success -> {
                    SearchState(
                        searchResult = Wrapper(
                            searchState.searchResult!!.data + result.data!!.data,
                            result.data.previousCursor,
                            result.data.nextCursor
                        )
                    )
                }

                is Resource.Error -> {
                    SearchState(
                        searchResult = searchState.searchResult,
                        error = result.message ?: "An unexpected error occurred"
                    )
                }

                is Resource.Loading -> {
                    SearchState(searchResult = searchState.searchResult, isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}