package com.hiebeler.loopy.data.remote

import com.hiebeler.loopy.data.remote.dto.AccountDto
import com.hiebeler.loopy.data.remote.dto.AccountWrapperDto
import com.hiebeler.loopy.data.remote.dto.FeedWrapperDto
import com.hiebeler.loopy.data.remote.dto.FollowersWrapperDto
import com.hiebeler.loopy.data.remote.dto.LoginModelDto
import com.hiebeler.loopy.data.remote.dto.MetaAccountDto
import com.hiebeler.loopy.data.remote.dto.NotificationsWrapperDto
import com.hiebeler.loopy.data.remote.dto.PostDto
import com.hiebeler.loopy.data.remote.dto.SearchWrapperDto
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
    fun getNotifications(
        @Query("cursor") nextCursor: String
    ): Call<NotificationsWrapperDto>

    @GET("api/v0/user/id/{accountid}?ext=1")
    fun getUser(
        @Path("accountid") accountId: String
    ): Call<AccountWrapperDto>

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
        @Path("accountid") accountId: String,
        @Query("cursor") nextCursor: String
    ): Call<FeedWrapperDto>

    @GET("api/v0/user/self/videos")
    fun getPostsOfOwnUser(): Call<FeedWrapperDto>

    @POST("api/v0/follow/{accountid}")
    fun followUser(
        @Path("accountid") accountId: String
    ): Call<MetaAccountDto>

    @POST("api/v0/unfollow/{accountid}")
    fun unfollowUser(
        @Path("accountid") accountId: String
    ): Call<MetaAccountDto>

    @POST("/auth/start?device_name=Loopy&build=10")
    fun login(
        @Query("email") email: String, @Query("password") password: String
    ): Call<LoginModelDto>

    @GET("api/v0/video/id/{postid}")
    fun getPost(
        @Path("postid") postId: String
    ): Call<PostDto>

    @POST("api/v0/like/video/{postid}")
    fun likePost(
        @Path("postid") postId: String
    ): Call<PostDto>

    @POST("api/v0/unlike/video/{postid}")
    fun unlikePost(
        @Path("postid") postId: String
    ): Call<PostDto>

    @POST("api/v0.5/search/users")
    fun search(
        @Query("q") query: String,
        @Query("cursor") nextCursor: String = ""
    ): Call<SearchWrapperDto>
}