package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class GetUser(
    private val appRepository: AppRepository
) {
    operator fun invoke(userId: String): Flow<Resource<User?>> = flow{
        val result =  appRepository.getUser(userId)
        emitAll(result)
    }
}