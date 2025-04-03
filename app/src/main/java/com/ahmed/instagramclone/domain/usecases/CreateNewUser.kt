package com.ahmed.instagramclone.domain.usecases

import com.ahmed.instagramclone.util.Resource
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class CreateNewUser(
    private val appRepository: AppRepository,
) {
    operator fun invoke(user: User, password: String): Flow<Resource<Unit>> = flow {
        val result = appRepository.createNewUser(user, password)
        emitAll(result)
    }
}