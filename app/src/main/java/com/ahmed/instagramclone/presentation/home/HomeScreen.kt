package com.ahmed.instagramclone.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.presentation.components.PostsList
import com.ahmed.instagramclone.util.Resource

@Composable
fun HomeScreen(state: Resource<List<PostWithAuthor>>?,navigateToStory: () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        when (state) {
            is Resource.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }

            is Resource.Success -> {
                state.data?.let {
                    PostsList(state.data, navigateToStory = navigateToStory )
                }

            }

            is Resource.Error -> {
                Toast.makeText(context, "Error !!!", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }


    }
}
