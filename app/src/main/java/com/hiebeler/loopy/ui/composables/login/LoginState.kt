package com.hiebeler.loopy.ui.composables.login

data class LoginState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String = ""
)
