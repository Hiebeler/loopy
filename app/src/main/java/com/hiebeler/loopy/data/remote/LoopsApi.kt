package com.hiebeler.loopy.data.remote

import com.hiebeler.loopy.data.remote.dto.AccountDto
import com.hiebeler.loopy.data.remote.dto.FeedWrapperDto
import com.hiebeler.loopy.data.remote.dto.FollowersWrapperDto
import com.hiebeler.loopy.data.remote.dto.LoginModelDto
import com.hiebeler.loopy.data.remote.dto.NotificationsWrapperDto
import com.hiebeler.loopy.domain.model.FollowersWrapper
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface LoopsApi {
    @GET("api/v0/feed/for-you")
    fun getForYouFeed(): Call<FeedWrapperDto>

    @GET("api/v0/feed/for-you")
    fun getForYouFeed(@Query("cursor") cursor: String): Call<FeedWrapperDto>

    @GET("api/v0/user/self")
    fun getOwnUser(): Call<AccountDto>

    @GET("api/v0/notifications/self")
    fun getNotifications(): Call<NotificationsWrapperDto>

    @GET("api/v0/user/id/{accountid}")
    fun getUser(
        @Path("accountid") accountId: String
    ): Call<AccountDto>

    @GET("api/v0/user/followers/byId/{accountid}")
    fun getFollowers(
        @Path("accountid") accountId: String
    ): Call<FollowersWrapperDto>

    @GET("api/v0/user/following/byId/{accountid}")
    fun getFollowing(
        @Path("accountid") accountId: String
    ): Call<FollowersWrapperDto>

    @GET("api/v0/user/videos/{accountid}")
    fun getPostsOfUser(
        @Path("accountid") accountId: String
    ): Call<FeedWrapperDto>

    @POST("/auth/start?device_name=Loopy&build=4")
    fun login(
        @Query("email") email: String, @Query("password") password: String
    ): Call<LoginModelDto>
}