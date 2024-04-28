package com.example.gitsearchtest.domain.repository

import com.example.gitsearchtest.domain.model.GitUsers
import com.example.gitsearchtest.domain.model.UserRepositoriesItem
import retrofit2.Response

interface IGetSearch {
     suspend fun getUsers(id: String): Response<GitUsers>
     suspend fun getRepos(url: String): List<UserRepositoriesItem>
}