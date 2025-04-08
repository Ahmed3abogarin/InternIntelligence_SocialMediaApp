package com.ahmed.instagramclone.di

import android.app.Application
import android.content.Context
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.domain.repository.ChatRepository
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.domain.usecases.CreateNewUser
import com.ahmed.instagramclone.domain.usecases.FollowUser
import com.ahmed.instagramclone.domain.usecases.GetPosts
import com.ahmed.instagramclone.domain.usecases.GetReels
import com.ahmed.instagramclone.domain.usecases.GetStory
import com.ahmed.instagramclone.domain.usecases.GetUser
import com.ahmed.instagramclone.domain.usecases.GetUserPosts
import com.ahmed.instagramclone.domain.usecases.GetUserStories
import com.ahmed.instagramclone.domain.usecases.LikePost
import com.ahmed.instagramclone.domain.usecases.SearchUser
import com.ahmed.instagramclone.domain.usecases.SignIn
import com.ahmed.instagramclone.domain.usecases.UnfollowUser
import com.ahmed.instagramclone.domain.usecases.UnlikePost
import com.ahmed.instagramclone.domain.usecases.UploadPost
import com.ahmed.instagramclone.domain.usecases.UploadStory
import com.ahmed.instagramclone.domain.usecases.message_usecases.ChatUseCases
import com.ahmed.instagramclone.domain.usecases.message_usecases.GetMessages
import com.ahmed.instagramclone.domain.usecases.message_usecases.SendMessage
import com.ahmed.instagramclone.repository.AppRepositoryImpl
import com.ahmed.instagramclone.repository.ChatRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFireStore() = Firebase.firestore

    @Provides
    @Singleton
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()

    @Provides
    @Singleton
    fun provideAppRepository(
        auth: FirebaseAuth,
        firebaseFireStore: FirebaseFirestore,
        storage: FirebaseStorage,
    )
            : AppRepository = AppRepositoryImpl(auth, firebaseFireStore, storage)


    @Provides
    @Singleton
    fun provideChatRepository(
        database: FirebaseDatabase,
        auth: FirebaseAuth,
    ): ChatRepository = ChatRepositoryImpl(database, auth)

    @Provides
    @Singleton
    fun provideChatUseCases(chatRepository: ChatRepository) = ChatUseCases(
        sendMessage = SendMessage(chatRepository),
        getMessages = GetMessages(chatRepository)
    )

    @Provides
    @Singleton
    fun provideAppUseCases(appRepository: AppRepository) = AppUseCases(
        createNewUser = CreateNewUser(appRepository),
        signIn = SignIn(appRepository),
        searchUser = SearchUser(appRepository),
        getPosts = GetPosts(appRepository),
        uploadPost = UploadPost(appRepository),
        getUser = GetUser(appRepository),
        getReels = GetReels(appRepository),
        followUser = FollowUser(appRepository),
        unfollowUser = UnfollowUser(appRepository),
        uploadStory = UploadStory(appRepository),
        getStory = GetStory(appRepository),
        getUserPosts = GetUserPosts(appRepository),
        getUserStories = GetUserStories(appRepository),
        likePost = LikePost(appRepository),
        unlikePost = UnlikePost(appRepository)
    )
}