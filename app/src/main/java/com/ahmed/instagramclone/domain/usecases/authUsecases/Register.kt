package com.ahmed.instagramclone.domain.usecases.authUsecases

import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AuthRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class Register(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(user: User, password: String): Flow<Resource<Unit>> = flow {
        val result = authRepository.createNewUser(user, password)
        emitAll(result)
    }
}