package com.hiebeler.loopy.ui.composables.explore

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hiebeler.loopy.ui.composables.CustomUser
import com.hiebeler.loopy.ui.composables.InfiniteListHandler

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreComposable(navController: NavController, viewModel: ExploreViewModel = hiltViewModel()) {
    val textFieldState = rememberTextFieldState()
    var expanded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(textFieldState.text) {
        viewModel.textInputChange(textFieldState.text.toString())
    }

    BackHandler(enabled = viewModel.searchState.searchResult != null && !expanded) {
        viewModel.searchState = SearchState()
        textFieldState.clearText()
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .semantics { isTraversalGroup = true }) {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 12.dp)
                .fillMaxWidth()
        ) {
            SearchBar(
                windowInsets = WindowInsets(top = 0.dp),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .semantics { traversalIndex = 0f },
                inputField = {
                    SearchBarDefaults.InputField(
                        state = textFieldState,
                        onSearch = { expanded = false },
                        expanded = expanded,
                        onExpandedChange = { expanded = it },
                        placeholder = { Text("Search Loops") },
                        leadingIcon = {
                            if (!expanded) {
                                Icon(Icons.Default.Search, contentDescription = null)
                            } else {
                                Icon(Icons.Outlined.ArrowBackIosNew,
                                    contentDescription = null,
                                    modifier = Modifier.clickable { expanded = false })
                            }
                        },
                    )
                },
                expanded = expanded,
                onExpandedChange = { expanded = it },
            ) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    viewModel.searchState.searchResult?.let { searchResult ->
                        searchResult.data.forEach { user ->
                            CustomUser(user, navController)
                        }
                    }
                }
            }
        }
        Box(
            Modifier.semantics { traversalIndex = 1f },
        )
        if (viewModel.searchState.searchResult != null) {
            viewModel.searchState.searchResult?.let { searchResult ->
                val lazyListSearchState = rememberLazyListState()
                LazyColumn(
                    state = lazyListSearchState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(searchResult.data) { user ->
                        CustomUser(user, navController)
                    }
                }
                InfiniteListHandler(lazyListSearchState) {
                    viewModel.loadMoreSearchResults(textFieldState.text.toString())
                }
            }
        }
    }
}