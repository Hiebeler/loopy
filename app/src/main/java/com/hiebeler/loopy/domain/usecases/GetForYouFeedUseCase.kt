package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetForYouFeedUseCase(
    private val timelineRepository: TimelineRepository
) {
    operator fun invoke(maxPostId: String = ""): Flow<Resource<Wrapper<Post>>> {
        return timelineRepository.getForYouFeed(maxPostId)
    }
}