package com.ahmed.instagramclone.domain.repository

import com.ahmed.instagramclone.domain.model.CommentWithUser
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.util.Resource
import kotlinx.coroutines.flow.Flow

interface PostRepository {
    fun getPosts(): Flow<Resource<List<PostWithAuthor>>>
    fun uploadPost(id: String, description: String, byteArray: ByteArray): Flow<Resource<Unit>>
    fun getUserPosts(userId: String): Flow<Resource<List<PostWithAuthor>>>
    fun likePost(postId: String): Flow<Resource<Unit>>
    fun unlikePost(postId: String): Flow<Resource<Unit>>
    fun commentPost(postId: String, comment: String): Flow<Resource<Unit>>
    fun getComments(postId: String): Flow<Resource<List<CommentWithUser>>>
}