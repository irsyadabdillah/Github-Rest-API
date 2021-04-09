package com.irzstudio.githubrestapi

import com.irzstudio.githubrestapi.datarepo.DataRepo
import com.irzstudio.githubrestapi.datauser.DataDetailUser
import com.irzstudio.githubrestapi.datauser.DataUser
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    fun getUser(
        @Query("q") query: String
    ): Call<DataUser>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") user: String): Call<DataDetailUser>

    @GET("rizmaulana/repos")
    fun getRepo(): Call<Array<DataRepo>>
}