package com.ahmed.instagramclone.repository

import android.net.Uri
import android.util.Log
import com.ahmed.instagramclone.domain.model.Post
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.Reel
import com.ahmed.instagramclone.domain.model.ReelWithAuthor
import com.ahmed.instagramclone.domain.model.Story
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AppRepository
import com.ahmed.instagramclone.util.Constants.USER_COLLECTION
import com.ahmed.instagramclone.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
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

    override fun getUser(userId: String): Flow<Resource<User?>> = callbackFlow {
        trySend(Resource.Loading())

        val listener = db.collection("Instagram_user")
            .document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Unexpected error"))
                    return@addSnapshotListener
                }

                val user = snapshot?.toObject(User::class.java)
                trySend(Resource.Success(user))
            }

        awaitClose { listener.remove() }
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

    override fun getReels() = flow {
        emit(Resource.Loading())
        val snapshot = db.collection("reels").get().await()
        val reels = snapshot.toObjects(Reel::class.java)

        val reelsWithAuthors = reels.mapNotNull { reel ->
            val authorFlow = getUser(reel.authorId)
                .filterIsInstance<Resource.Success<User>>()
                .map { it.data }
                .catch { emit(User()) }
                .firstOrNull()

            ReelWithAuthor(post = reel, author = authorFlow ?: User())
        }


        emit(Resource.Success(reelsWithAuthors))
    }.catch { e ->
        Log.v("GETPOSTSTOOL", e.message.toString())
        emit(Resource.Error(e.message.toString()))
    }

    override fun uploadPost(id: String, description: String, byteArray: ByteArray) = flow {
        val postsStorage = storage.reference.child("posts/images/$id")
        val result = postsStorage.putBytes(byteArray).await()
        val downloadUrl = result.storage.downloadUrl.await().toString()


        try {
            emit(Resource.Loading())

            val post = Post(
                authorId = auth.currentUser!!.uid,
                image = downloadUrl,
                description = description,
            )


            db.collection("posts").add(post)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
            Log.v("UPLOADPOST", e.message.toString())

        }


    }

    override fun followUser(currentUserId: String, targetUserId: String) = flow {
        emit(Resource.Loading())
        val currentUserRef = db.collection("Instagram_user").document(currentUserId)
        val targetUserRef = db.collection("Instagram_user").document(targetUserId)
        db.runBatch { batch ->
            // add current user to target user followers ( follow person)
            batch.update(targetUserRef, "followers", FieldValue.arrayUnion(currentUserId))

            // add target user to current following list
            batch.update(currentUserRef, "following", FieldValue.arrayUnion(targetUserId))

        }
        emit(Resource.Success(Unit))
    }.catch { e ->
        Log.v("GETUSERTOOL", e.message.toString())
        emit(Resource.Error(e.message.toString()))
    }

    override fun unfollowUser(currentUserId: String, targetUserId: String) = flow {
        emit(Resource.Loading())
        val currentUserRef = db.collection("Instagram_user").document(currentUserId)
        val targetUserRef = db.collection("Instagram_user").document(targetUserId)
        db.runBatch { batch ->
            // remove current user from target user followers ( unfollow person)
            Log.v("TAGY", "current user Id = $currentUserId")
            Log.v("TAGY", "target user Id = $targetUserId")
            batch.update(targetUserRef, "followers", FieldValue.arrayRemove(currentUserId))

            // remove target user from current following list
            batch.update(currentUserRef, "following", FieldValue.arrayRemove(targetUserId))

        }
        emit(Resource.Success(Unit))
    }.catch { e ->
        Log.v("GETUSERTOOL", e.message.toString())
        emit(Resource.Error(e.message.toString()))
    }

    override fun uploadStory(userId: String, videoUri: Uri): Flow<Resource<Unit>> = flow {
        val postsStorage = storage.reference.child("stories/${auth.currentUser!!.uid}/${UUID.randomUUID()}")
        val result = postsStorage.putFile(videoUri).await()

        val downloadUrl = result.storage.downloadUrl.await().toString()


        try {
            emit(Resource.Loading())
            val story = Story(downloadUrl)
            db.collection("Instagram_user").document(auth.currentUser!!.uid).collection("story").add(story)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
            Log.v("UPLOADPOST", e.message.toString())

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
}