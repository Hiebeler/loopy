package com.hiebeler.loopy.ui.composables.profile.followers

import com.hiebeler.loopy.domain.model.Account
import com.hiebeler.loopy.domain.model.Wrapper

data class FollowersState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val followers: Wrapper<Account>? = null,
    val error: String = ""
)
