package com.example.gitsearchtest.domain.use_cases

import com.example.gitsearchtest.data.repository.GetSearchImpl
import com.example.gitsearchtest.domain.model.GitUsers
import retrofit2.Response

class GetUsersUseCase (val repository: GetSearchImpl) {
    suspend fun getUsers(id: String): Response<GitUsers> {
        return repository.getUsers(id)
    }
}