package com.ahmed.instagramclone.domain.repository

import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.util.Resource
import com.ahmed.instagramclone.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AppRepository {
     fun createNewUser(user: User, password: String): Flow<Resource<Unit>>

     fun signIn(email: String, password: String): Flow<Resource<Unit>>

     fun searchUser(searchQuery: String): Flow<Resource<List<User>>>

     fun getUser(userId: String): Flow<Resource<User?>>

     fun getPosts(): Flow<Resource<List<PostWithAuthor>>>
}