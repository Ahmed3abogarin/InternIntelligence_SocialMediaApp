package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

class SearchUser(
    private val appRepository: AppRepository
) {
    operator fun invoke(searchQuery: String): Flow<Resource<List<User>>>{
        return appRepository.searchUser(searchQuery)
    }
}