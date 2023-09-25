package com.example.github_user_application.data.remote.retrofit

import com.example.github_user_application.data.remote.response.DetailUserResponse
import com.example.github_user_application.data.remote.response.GitHubResponse
import com.example.github_user_application.data.remote.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(
        @Query("q") id: String
    ): Call<GitHubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username")username:String,
        @Query("token")token:String
    ):Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getUserFollowers(
        @Path("username") username: String,
        @Query("token")token:String
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getUserFollowing(
        @Path("username") username: String,
        @Query("token")token:String
    ): Call<List<ItemsItem>>

}