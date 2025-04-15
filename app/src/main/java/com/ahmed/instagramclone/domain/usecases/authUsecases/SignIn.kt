package com.ahmed.instagramclone.domain.usecases.authUsecases

import com.ahmed.instagramclone.domain.repository.AuthRepository
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class SignIn(
    private val authRepository: AuthRepository,
) {
    operator fun invoke(email: String, password: String): Flow<Resource<Unit>> = flow {
        val result = authRepository.signIn(email = email, password = password)
        emitAll(result)
    }
}