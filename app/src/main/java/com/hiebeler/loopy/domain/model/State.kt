package com.hiebeler.loopy.domain.model

data class State<T>(
    val data: T,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String = ""
)