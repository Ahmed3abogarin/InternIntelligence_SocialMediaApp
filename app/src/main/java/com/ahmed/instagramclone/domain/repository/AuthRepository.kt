package com.ahmed.instagramclone.domain.repository

import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun createNewUser(user: User, password: String): Flow<Resource<Unit>>

    fun signIn(email: String, password: String): Flow<Resource<Unit>>

}