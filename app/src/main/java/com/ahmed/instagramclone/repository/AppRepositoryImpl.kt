package com.ahmed.instagramclone.repository

import android.util.Log
import com.ahmed.instagramclone.Constants.USER_COLLECTION
import com.ahmed.instagramclone.RegisterValidation
import com.ahmed.instagramclone.Resource
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.validateEmail
import com.ahmed.instagramclone.validatePassword
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) : AppRepository {

//    private val _validation = Channel<RegisterFieldsState>()
//    val  validation = _validation.receiveAsFlow()

    override fun createNewUser(user: User, password: String) = flow {


        if (!checkValidation(user = user, password = password)){
            Log.v("TAGYTOOL","!checkValidation")

            return@flow
        }

        try {
            emit(Resource.Loading())

            val authResult = auth.createUserWithEmailAndPassword(user.email, password).await()
            authResult.user?.let {
                saveUserInfo(it.uid, user)
                emit(Resource.Success(Unit))
            } ?: emit(Resource.Error("User creation failed"))

        } catch (e: Exception) {
            Log.v("TAGYTOOL",e.message.toString())

            emit(Resource.Error(e.message ?: "Unknown error"))
        }


    }

    private fun saveUserInfo(uid: String, user: User) {
        db.collection(USER_COLLECTION)
            .document(uid)
            .set(user)
//            .addOnSuccessListener {
//                // Success >>>>>>>>
//            }
//            .addOnFailureListener {
//                // Error ><><<><<><
//            }

    }

    private fun checkValidation(user: User, password: String): Boolean {
        val emailValidation = validateEmail(user.email)
        val passwordValidation = validatePassword(password)

        val shouldRegister = emailValidation is RegisterValidation.Success
                && passwordValidation is RegisterValidation.Success

        return shouldRegister
    }
}