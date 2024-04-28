package com.example.gitsearchtest.data.api

import com.example.gitsearchtest.domain.model.GitUsers
import com.example.gitsearchtest.domain.model.UserRepositoriesItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface IGitHubAPI {

    @GET("/search/users?")
    suspend fun getUsers(
        @Query("q") user: String,
    ): Response<GitUsers>

    @GET
    suspend fun getRepos(@Url url: String): List<UserRepositoriesItem>
}