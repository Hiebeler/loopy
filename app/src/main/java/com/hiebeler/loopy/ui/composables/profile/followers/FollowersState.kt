package com.hiebeler.loopy.ui.composables.profile.followers

import com.hiebeler.loopy.domain.model.FollowersWrapper

data class FollowersState(
    val isLoading: Boolean = false,
    val refreshing: Boolean = false,
    val followers: FollowersWrapper? = null,
    val error: String = ""
)
