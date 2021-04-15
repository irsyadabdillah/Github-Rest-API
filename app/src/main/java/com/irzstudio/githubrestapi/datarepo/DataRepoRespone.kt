package com.irzstudio.githubrestapi.datarepo

data class DataRepoRespone(
    val name: String,
    val owner: OwnerRepoResponse,
    val description: String
)
