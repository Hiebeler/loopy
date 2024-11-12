package com.hiebeler.loopy.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Media(
    val height: Int, val srcUrl: String, val thumbnail: String, val width: Int
)
