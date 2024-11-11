package com.hiebeler.loopy.data.remote

import com.hiebeler.loopy.data.remote.dto.AccountDto
import com.hiebeler.loopy.data.remote.dto.LoginModelDto
import com.hiebeler.loopy.domain.model.LoginModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoopsApi {
    @GET("api/pixelfed/v2/discover/posts/trending")
    fun getAccount(@Query("range") range: String): Call<AccountDto>

    @POST("/auth/start?device_name=Third Party Android Client&build=4")
    fun login(@Query("email") email: String, @Query("password") password: String): Call<LoginModelDto>
}