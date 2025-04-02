package com.ahmed.instagramclone.domain.repository

import com.ahmed.instagramclone.Resource
import com.ahmed.instagramclone.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AppRepository {
     fun createNewUser(user: User, password: String): Flow<Resource<Unit>>

     fun signIn(email: String, password: String): Flow<Resource<Unit>>
}