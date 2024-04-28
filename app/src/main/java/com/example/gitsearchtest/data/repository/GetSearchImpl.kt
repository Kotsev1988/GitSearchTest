package com.example.gitsearchtest.data.repository

import com.example.gitsearchtest.data.api.IGitHubAPI
import com.example.gitsearchtest.domain.model.GitUsers
import com.example.gitsearchtest.domain.model.UserRepositoriesItem
import com.example.gitsearchtest.domain.repository.IGetSearch
import retrofit2.Response

class GetSearchImpl(
    private val api: IGitHubAPI
): IGetSearch {
    override suspend fun getUsers(id: String): Response<GitUsers> =
        api.getUsers(id)


    override suspend fun getRepos(url: String): List<UserRepositoriesItem> =
        api.getRepos(url)

}