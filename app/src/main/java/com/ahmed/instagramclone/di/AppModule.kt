package com.ahmed.instagramclone.di

import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.domain.usecases.AppUseCases
import com.ahmed.instagramclone.domain.usecases.CreateNewUser
import com.ahmed.instagramclone.domain.usecases.SignIn
import com.ahmed.instagramclone.repository.AppRepositoryImpl
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
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
    fun provideAppRepository(
        auth: FirebaseAuth,
        firebaseFireStore: FirebaseFirestore
    )
    : AppRepository = AppRepositoryImpl(auth,firebaseFireStore)

    @Provides
    @Singleton
    fun provideAppUseCases(appRepository: AppRepository) = AppUseCases(
        createNewUser = CreateNewUser(appRepository),
        signIn = SignIn(appRepository)
    )
}