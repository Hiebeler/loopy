package com.hiebeler.loopy.domain.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.FeedWrapper
import com.hiebeler.loopy.domain.model.Post
import kotlinx.coroutines.flow.Flow

interface TimelineRepository {

    fun getForYouFeed(maxPostId: String = ""): Flow<Resource<FeedWrapper>>
}