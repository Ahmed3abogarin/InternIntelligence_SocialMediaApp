package com.ahmed.instagramclone.di

import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.domain.repository.ChatRepository
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.domain.usecases.authUsecases.Register
import com.ahmed.instagramclone.domain.usecases.FollowUser
import com.ahmed.instagramclone.domain.usecases.GetReels
import com.ahmed.instagramclone.domain.usecases.GetStory
import com.ahmed.instagramclone.domain.usecases.GetUser
import com.ahmed.instagramclone.domain.usecases.GetUserStories
import com.ahmed.instagramclone.domain.usecases.SearchUser
import com.ahmed.instagramclone.domain.usecases.authUsecases.SignIn
import com.ahmed.instagramclone.domain.usecases.UnfollowUser
import com.ahmed.instagramclone.domain.usecases.UploadStory
import com.ahmed.instagramclone.domain.usecases.message_usecases.ChatUseCases
import com.ahmed.instagramclone.domain.usecases.message_usecases.GetMessages
import com.ahmed.instagramclone.domain.usecases.message_usecases.SendMessage
import com.ahmed.instagramclone.data.repository.AppRepositoryImpl
import com.ahmed.instagramclone.data.repository.AuthRepositoryImpl
import com.ahmed.instagramclone.data.repository.ChatRepositoryImpl
import com.ahmed.instagramclone.domain.repository.AuthRepository
import com.ahmed.instagramclone.domain.usecases.SaveUser
import com.ahmed.instagramclone.domain.usecases.SaveUserWithNewImage
import com.ahmed.instagramclone.domain.usecases.authUsecases.AuthUseCases
import com.ahmed.instagramclone.domain.usecases.message_usecases.GetLastMessage
import com.ahmed.instagramclone.domain.usecases.message_usecases.GetSenders
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
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firebaseFireStore: FirebaseFirestore,
    ): AuthRepository = AuthRepositoryImpl(auth,firebaseFireStore)

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
        firebaseFireStore: FirebaseFirestore,
        auth: FirebaseAuth,
    ): ChatRepository = ChatRepositoryImpl(firebaseFireStore,database, auth)

    @Provides
    @Singleton
    fun provideAuthUseCases(authRepository: AuthRepository)= AuthUseCases(
        createNewUser = Register(authRepository),
        signIn = SignIn(authRepository)
    )

    @Provides
    @Singleton
    fun provideChatUseCases(chatRepository: ChatRepository) = ChatUseCases(
        sendMessage = SendMessage(chatRepository),
        getMessages = GetMessages(chatRepository),
        getSenders = GetSenders(chatRepository),
        getLastMessage = GetLastMessage(chatRepository)
    )

    @Provides
    @Singleton
    fun provideAppUseCases(appRepository: AppRepository) = AppUseCases(
        searchUser = SearchUser(appRepository),
        getUser = GetUser(appRepository),
        getReels = GetReels(appRepository),
        followUser = FollowUser(appRepository),
        unfollowUser = UnfollowUser(appRepository),
        uploadStory = UploadStory(appRepository),
        getStory = GetStory(appRepository),
        getUserStories = GetUserStories(appRepository),
        saveUser = SaveUser(appRepository),
        saveUserWithNewImage = SaveUserWithNewImage(appRepository)
    )
}