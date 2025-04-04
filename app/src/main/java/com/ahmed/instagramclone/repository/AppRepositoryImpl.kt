package com.ahmed.instagramclone.repository

import android.util.Log
import com.ahmed.instagramclone.domain.model.Post
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Constants.USER_COLLECTION
import com.ahmed.instagramclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
) : AppRepository {

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
            auth.signInWithEmailAndPassword(email, password)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            Log.v("TAGYTOOL", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }

    }

    override fun searchUser(searchQuery: String) = flow {
        try {
            emit(Resource.Loading())

            val querySnapshot = db.collection("Instagram_user")
                .whereGreaterThanOrEqualTo("firstName", searchQuery)
                .whereLessThanOrEqualTo("firstName", searchQuery + "\uf8ff")
                .get()
                .await()

            val users = querySnapshot.toObjects(User::class.java)
            emit(Resource.Success(users))

        } catch (e: Exception) {
            Log.v("TAGYTOOL", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getUser(userId: String) = flow {
        emit(Resource.Loading())
        val user = db.collection("Instagram_user")
            .document(userId)
            .get()
            .await()
            .toObject(User::class.java)

        emit(Resource.Success(user))
    }.catch { e ->
        Log.v("GETUSERTOOL", e.message.toString())
        emit(Resource.Error(e.message.toString()))
    }

    override fun getPosts() = flow {
        emit(Resource.Loading())

        val snapshot = db.collection("posts")
            .orderBy("timestamp").get().await()
        val posts = snapshot.toObjects(Post::class.java)

        val postsWithAuthors = posts.mapNotNull { post ->
            val authorFlow = getUser(post.authorId)
                .filterIsInstance<Resource.Success<User>>()
                .map { it.data }
                .catch { emit(User()) }
                .firstOrNull()

            PostWithAuthor(post = post, author = authorFlow ?: User())
        }

        emit(Resource.Success(postsWithAuthors))
    }.catch { e ->
        Log.v("GETPOSTSTOOL", e.message.toString())
        emit(Resource.Error(e.message.toString()))
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
}