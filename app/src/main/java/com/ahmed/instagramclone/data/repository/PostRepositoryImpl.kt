package com.ahmed.instagramclone.data.repository

import android.util.Log
import com.ahmed.instagramclone.domain.model.Comment
import com.ahmed.instagramclone.domain.model.CommentWithUser
import com.ahmed.instagramclone.domain.model.Post
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.domain.repository.PostRepository
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
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage,
) : PostRepository {
    override fun getPosts() = callbackFlow {
        trySend(Resource.Loading())

        val listenerRegistration = db.collection("posts")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    trySend(Resource.Error(error.message ?: "Unexpected error"))
                    return@addSnapshotListener
                }

                val posts = snapshot?.toObjects(Post::class.java)

                if (posts != null) {
                    // Launch a coroutine to fetch authors
                    launch {
                        val postsWithAuthors = posts.map { post ->
                            val author = getUser(post.authorId)
                                .filterIsInstance<Resource.Success<User>>()
                                .map { it.data }
                                .catch { emit(User()) }
                                .firstOrNull() ?: User()

                            PostWithAuthor(post = post, author = author)
                        }
                        trySend(Resource.Success(postsWithAuthors))
                    }
                }
            }

        awaitClose {
            listenerRegistration.remove()
        }
    }

    override fun uploadPost(id: String, description: String, byteArray: ByteArray) = flow {
        val postsStorage = storage.reference.child("posts/images/$id")
        val result = postsStorage.putBytes(byteArray).await()
        val downloadUrl = result.storage.downloadUrl.await().toString()


        try {
            emit(Resource.Loading())

            val post = Post(
                id = id,
                authorId = auth.currentUser!!.uid,
                image = downloadUrl,
                description = description,
            )


            db.collection("posts").document(id).set(post)
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
            Log.v("UPLOADPOST", e.message.toString())

        }


    }

    override fun getUserPosts(userId: String) = flow {
        emit(Resource.Loading())

        val snapshot = db.collection("posts").whereEqualTo("authorId", userId).get().await()
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
        Log.v("MYPOSTS", e.message.toString())
        emit(Resource.Error(e.message.toString()))
    }

    override fun likePost(postId: String) = flow {
        emit(Resource.Loading())
        val currentUserRef = db.collection("posts").document(postId)
        currentUserRef.update("likes", FieldValue.arrayUnion(auth.currentUser!!.uid))

        emit(Resource.Success(Unit))
    }.catch { e ->
        Log.v("GETUSERTOOL", e.message.toString())
        emit(Resource.Error(e.message.toString()))
    }

    override fun unlikePost(postId: String) = flow {
        emit(Resource.Loading())
        val currentUserRef = db.collection("posts").document(postId)
        currentUserRef.update("likes", FieldValue.arrayRemove(auth.currentUser!!.uid))

        emit(Resource.Success(Unit))
    }.catch { e ->
        Log.v("GETUSERTOOL", e.message.toString())
        emit(Resource.Error(e.message.toString()))
    }

    override fun commentPost(
        postId: String,
        comment: String,
    ) = flow {
        emit(Resource.Loading())
        try {
            val userComment = Comment(userId = auth.currentUser!!.uid, commentTxt = comment)
            db.collection("posts").document(postId).collection("comments").add(userComment)
            emit(Resource.Success(Unit))

        } catch (e: Exception) {
            Log.v("Comment", e.message.toString())
            emit(Resource.Error(e.message.toString()))

        }
    }

    override fun getComments(postId: String): Flow<Resource<List<CommentWithUser>>> = callbackFlow {
        try {
            trySend(Resource.Loading())

            val listener =
                db.collection("posts").document(postId).collection("comments").orderBy("time")
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            trySend(Resource.Error(error.message ?: "Unexpected error"))
                            return@addSnapshotListener
                        }

                        val comments = value?.toObjects(Comment::class.java) ?: emptyList()

                        launch {
                            val commentsWithUsers = comments.map { comment ->
                                val user = getUser(comment.userId)
                                    .firstOrNull {
                                        it is Resource.Success
                                    }?.let {
                                        (it as Resource.Success).data
                                    } ?: User() // fallback

                                CommentWithUser(user = user, comment = comment)
                            }

                            trySend(Resource.Success(commentsWithUsers))
                        }
                    }

            awaitClose { listener.remove() }

        } catch (e: Exception) {
            trySend(Resource.Error(e.message ?: "Unexpected error"))
        }
    }

    private fun getUser(userId: String): Flow<Resource<User?>> = callbackFlow {
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
}