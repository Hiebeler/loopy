package com.hiebeler.loopy.domain.usecases

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.domain.model.FeedWrapper
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.repository.TimelineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetForYouFeedUseCase(
    private val timelineRepository: TimelineRepository
) {
    operator fun invoke(maxPostId: String = ""): Flow<Resource<FeedWrapper?>> = flow {
        emit(Resource.Loading())
        timelineRepository.getForYouFeed(maxPostId).collect { timeline ->
            if (timeline is Resource.Success) {
                val res: FeedWrapper? = timeline.data
                emit(Resource.Success(res))
            } else {
                emit(Resource.Error(timeline.message ?: "Something went wrong"))
            }
        }
    }
}