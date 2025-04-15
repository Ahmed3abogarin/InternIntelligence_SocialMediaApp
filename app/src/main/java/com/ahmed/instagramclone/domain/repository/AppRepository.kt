package com.ahmed.instagramclone.domain.repository

import android.net.Uri
import com.ahmed.instagramclone.domain.model.ReelWithAuthor
import com.ahmed.instagramclone.domain.model.Story
import com.ahmed.instagramclone.domain.model.StoryWithAuthor
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun searchUser(searchQuery: String): Flow<Resource<List<User>>>

    fun getUser(userId: String): Flow<Resource<User?>>


    fun getReels(): Flow<Resource<List<ReelWithAuthor>>>


    fun followUser(currentUserId: String, targetUserId: String): Flow<Resource<Unit>>

    fun unfollowUser(currentUserId: String, targetUserId: String): Flow<Resource<Unit>>

    fun uploadStory(userId: String, videoUri: Uri): Flow<Resource<Unit>>

    fun getUserStory(userId: String): Flow<Resource<List<Story>>>

    fun getUserStories(): Flow<Resource<MutableList<List<StoryWithAuthor>>>>

    fun saveUserInfo(user: User,shouldRetrieveOldImage: Boolean): Flow<Resource<Unit>>

    fun saveUserInfoWithNewImage(user: User, byteArray: ByteArray): Flow<Resource<Unit>>
}