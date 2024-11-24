package com.hiebeler.loopy.ui.composables.profile.own_profile

import com.hiebeler.loopy.domain.model.Account

data class UserState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val user: Account? = null,
    val error: String = ""
)