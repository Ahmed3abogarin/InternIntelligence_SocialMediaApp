package com.ahmed.instagramclone.data.repository

import android.util.Log
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AuthRepository
import com.ahmed.instagramclone.util.Constants.USER_COLLECTION
import com.ahmed.instagramclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore
): AuthRepository {

    override fun createNewUser(user: User, password: String) = flow {
        try {
            emit(Resource.Loading())
            val authResult = auth.createUserWithEmailAndPassword(user.email, password).await()
            authResult.user?.let {
                saveUserInfo(it.uid, user)
                emit(Resource.Success(Unit))
            } ?: emit(Resource.Error("User creation failed"))

        } catch (e: Exception) {
            Log.v("TAGYTOOL", e.message.toString())
            emit(Resource.Error(e.message ?: "Unknown error"))
        }


    }

    override fun signIn(email: String, password: String) = flow {
        try {
            emit(Resource.Loading())
            auth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            Log.v("TAGYTOOL", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }

    }


    private fun saveUserInfo(uid: String, user: User) {
        db.collection(USER_COLLECTION)
            .document(uid)
            .set(user.copy(userId = uid))
//            .addOnSuccessListener {
//                // Success >>>>>>>>
//            }
//            .addOnFailureListener {
//                // Error ><><<><<><
//            }

    }
}