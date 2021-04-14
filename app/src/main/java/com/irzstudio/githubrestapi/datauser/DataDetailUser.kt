package com.irzstudio.githubrestapi.datauser

data class DataDetailUser(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val name: String,
    val blog: String,
    val location: String,
    val email: String,
    val bio: String,
    val followers: Int,
    val following: Int,
    val starred_url:String,
    val organizations_url: String,
    val repos_url: String
)
