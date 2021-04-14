package com.irzstudio.githubrestapi

import com.irzstudio.githubrestapi.datarepo.DataRepoRespone
import com.irzstudio.githubrestapi.datauser.DataDetailUser
import com.irzstudio.githubrestapi.datauser.DataUserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<DataUserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DataDetailUser>

    @GET("rizmaulana/repos")
    fun getRepo(): Call<Array<DataRepoRespone>>
}