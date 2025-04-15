package com.ahmed.instagramclone.data.repository

import android.net.Uri
import android.util.Log
import com.ahmed.instagramclone.domain.model.Reel
import com.ahmed.instagramclone.domain.model.ReelWithAuthor
import com.ahmed.instagramclone.domain.model.Story
import com.ahmed.instagramclone.domain.model.StoryWithAuthor
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.AppRepository
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
        val postsStorage =
            storage.reference.child("stories/${auth.currentUser!!.uid}/${UUID.randomUUID()}")
        val result = postsStorage.putFile(videoUri).await()

        val downloadUrl = result.storage.downloadUrl.await().toString()


        try {
            emit(Resource.Loading())
            val author = getUser(auth.currentUser!!.uid).filterIsInstance<Resource.Success<User>>()
                .map { it.data }.catch { emit(User()) }.firstOrNull()
            val story =
                Story(
                    authorId = auth.currentUser!!.uid,
                    username = author!!.firstName + " " + author.lastName,
                    videoUrl = downloadUrl
                )
            db.collection("Instagram_user").document(auth.currentUser!!.uid).collection("story")
                .add(story)
            db.collection("Instagram_user").document(auth.currentUser!!.uid)
                .update("hasStory", true)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
            Log.v("UPLOADPOST", e.message.toString())

        }


    }

    override fun getUserStory(userId: String): Flow<Resource<List<Story>>> = flow {
        emit(Resource.Loading())
        try {
            val ref =
                db.collection("Instagram_user").document(userId).collection("story").get().await()
            val userStory = ref.toObjects(Story::class.java)

            emit(Resource.Success(userStory))

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }

    }


    override fun getUserStories() = flow {
        emit(Resource.Loading())
        val currentUser = getUser(auth.currentUser!!.uid)
        val ss = mutableListOf<List<StoryWithAuthor>>()

        try {
            currentUser.collect {
                when (it) {
                    is Resource.Success -> {
                        it.data?.followers?.forEach { id ->
                            val ref =
                                db.collection("Instagram_user").document(id).collection("story")
                                    .get().await()
                            val userStory = ref.toObjects(Story::class.java)


                            val storyWithAuthor = userStory.mapNotNull { story ->
                                val author = getUser(story.authorId)
                                    .filterIsInstance<Resource.Success<User>>()
                                    .map { user -> user.data }
                                    .catch { emit(User()) }
                                    .firstOrNull()

                                StoryWithAuthor(story = story, author = author ?: User())
                            }

//                            storyWithAuthor[0].let {
//                                Log.v("STORY", it.toString())
//                            }

                            userStory.let {
                                ss.add(storyWithAuthor)
                            }
                        }
                        emit(Resource.Success(ss))
                    }

                    else -> Unit
                }
            }

        } catch (e: Exception) {
            Log.v("StoryUSERS", e.message.toString())
            emit(Resource.Error(e.message.toString()))
        }
    }


    override fun saveUserInfo(user: User, shouldRetrieveOldImage: Boolean) = flow {
        emit(Resource.Loading())
        db.runTransaction { transaction ->

            val documentRef = db.collection("Instagram_user").document(auth.currentUser!!.uid)


            if (shouldRetrieveOldImage) {
                val oldUser = transaction.get(documentRef).toObject(User::class.java)
                val updatedUser = user.copy(imagePath = oldUser?.imagePath ?: "")
                transaction.update(documentRef, "", "")
                transaction.set(documentRef, updatedUser)
            } else {
                transaction.set(documentRef, user)
            }

        }

        emit(Resource.Success(Unit))
    }.catch { e ->
        Log.v("GETUSERTOOL", e.message.toString())
        emit(Resource.Error(e.message.toString()))
    }

    override fun saveUserInfoWithNewImage(user: User, byteArray: ByteArray) = flow {
        emit(Resource.Loading())

        val imageDirectory =
            storage.reference.child("profileImages/${auth.uid}/${UUID.randomUUID()}")
        val result =
            imageDirectory.putBytes(byteArray).await() // await: gonna suspend this function
        val imageUrl = result.storage.downloadUrl.await().toString()
        saveUserInfo(
            user = user.copy(imagePath = imageUrl),
            shouldRetrieveOldImage = false
        ).collect { emit(it) }

    }.catch { e ->
        Log.v("GETUSERTOOL", e.message.toString())
        emit(Resource.Error(e.message.toString()))
    }

}