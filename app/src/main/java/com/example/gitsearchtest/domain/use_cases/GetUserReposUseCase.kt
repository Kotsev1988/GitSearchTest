package com.example.gitsearchtest.domain.use_cases

import com.example.gitsearchtest.data.repository.GetSearchImpl
import com.example.gitsearchtest.domain.model.UserRepositoriesItem

class GetUserReposUseCase (val repository: GetSearchImpl) {
    suspend fun getDetailInfo(url: String): List<UserRepositoriesItem> {
        return repository.getRepos(url)
    }
}