package com.hiebeler.loopy.data.repository

import com.hiebeler.loopy.common.Resource
import com.hiebeler.loopy.data.remote.LoopsApi
import com.hiebeler.loopy.data.remote.dto.FeedWrapperDto
import com.hiebeler.loopy.data.remote.dto.PostDto
import com.hiebeler.loopy.domain.model.Post
import com.hiebeler.loopy.domain.model.Wrapper
import com.hiebeler.loopy.domain.repository.TimelineRepository
import com.hiebeler.loopy.utils.NetworkCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TimelineRepositoryImpl @Inject constructor(
    private val loopsApi: LoopsApi
): TimelineRepository {

    override fun getForYouFeed(maxPostId: String): Flow<Resource<Wrapper<Post>>> {
        return if (maxPostId.isNotEmpty()) {
            NetworkCall<Wrapper<Post>, FeedWrapperDto>().makeCall(
                loopsApi.getForYouFeed(
                    maxPostId
                )
            )
        } else {
            NetworkCall<Wrapper<Post>, FeedWrapperDto>().makeCall(loopsApi.getForYouFeed())
        }
    }
}