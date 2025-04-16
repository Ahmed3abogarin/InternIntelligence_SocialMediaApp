package com.ahmed.instagramclone.di

import com.ahmed.instagramclone.data.repository.PostRepositoryImpl
import com.ahmed.instagramclone.domain.repository.PostRepository
import com.ahmed.instagramclone.domain.usecases.posts_usecases.AddComment
import com.ahmed.instagramclone.domain.usecases.posts_usecases.GetComments
import com.ahmed.instagramclone.domain.usecases.posts_usecases.GetPosts
import com.ahmed.instagramclone.domain.usecases.posts_usecases.GetUserPosts
import com.ahmed.instagramclone.domain.usecases.posts_usecases.LikePost
import com.ahmed.instagramclone.domain.usecases.posts_usecases.PostUseCases
import com.ahmed.instagramclone.domain.usecases.posts_usecases.UnlikePost
import com.ahmed.instagramclone.domain.usecases.posts_usecases.UploadPost
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PostModule {

    @Provides
    @Singleton
    fun providePostRepository(
        auth: FirebaseAuth,
        firebaseFireStore: FirebaseFirestore,
        storage: FirebaseStorage,
    ): PostRepository = PostRepositoryImpl(auth, firebaseFireStore, storage)


    @Provides
    @Singleton
    fun providePostUseCases(postRepository: PostRepository) = PostUseCases(
        getPosts = GetPosts(postRepository),
        getUserPosts = GetUserPosts(postRepository),
        uploadPost = UploadPost(postRepository),
        likePost = LikePost(postRepository),
        unlikePost = UnlikePost(postRepository),
        getComments = GetComments(postRepository),
        addComment = AddComment(postRepository)
    )
}