package com.ahmed.instagramclone.domain.repository

import android.net.Uri
import com.ahmed.instagramclone.domain.model.CommentWithUser
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.ReelWithAuthor
import com.ahmed.instagramclone.domain.model.Story
import com.ahmed.instagramclone.domain.model.StoryWithAuthor
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    fun createNewUser(user: User, password: String): Flow<Resource<Unit>>

    fun signIn(email: String, password: String): Flow<Resource<Unit>>

    fun searchUser(searchQuery: String): Flow<Resource<List<User>>>

    fun getUser(userId: String): Flow<Resource<User?>>

    fun getPosts(): Flow<Resource<List<PostWithAuthor>>>

    fun getReels(): Flow<Resource<List<ReelWithAuthor>>>

    fun uploadPost(id: String, description: String, byteArray: ByteArray): Flow<Resource<Unit>>

    fun followUser(currentUserId: String, targetUserId: String): Flow<Resource<Unit>>

    fun unfollowUser(currentUserId: String, targetUserId: String): Flow<Resource<Unit>>

    fun uploadStory(userId: String, videoUri: Uri): Flow<Resource<Unit>>

    fun getUserStory(userId: String): Flow<Resource<List<Story>>>

    fun getUserPosts(userId: String): Flow<Resource<List<PostWithAuthor>>>

    fun getUserStories(): Flow<Resource<MutableList<List<StoryWithAuthor>>>>

    fun likePost(postId: String): Flow<Resource<Unit>>
    fun unlikePost(postId: String): Flow<Resource<Unit>>

    fun commentPost(postId: String, comment: String): Flow<Resource<Unit>>

    fun getComments(postId: String): Flow<Resource<List<CommentWithUser>>>
}