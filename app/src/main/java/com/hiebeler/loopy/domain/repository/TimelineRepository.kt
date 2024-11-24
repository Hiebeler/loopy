package com.hiebeler.loopy.domain.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.Wrapper
import kotlinx.coroutines.flow.Flow

interface TimelineRepository {

    fun getForYouFeed(maxPostId: String = ""): Flow<Resource<Wrapper<Post>>>
}