package com.ahmed.instagramclone.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ahmed.instagramclone.domain.model.PostWithAuthor
import com.ahmed.instagramclone.domain.model.StoryWithAuthor
import com.ahmed.instagramclone.presentation.components.PostsList
import com.ahmed.instagramclone.util.Resource

@Composable
fun HomeScreen(
    state: Resource<List<PostWithAuthor>>?,
    storyState: Resource<MutableList<List<StoryWithAuthor>>>?,
    navigateToStory: () -> Unit,
    navigateToUserStory: (StoryWithAuthor) -> Unit,
    onCommentClicked: (String) -> Unit,
    event: (PostEvent) -> Unit,
    navigateToMessages: () -> Unit,
    currentUserId: String?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        PostsList(
            state = state,
            stories = storyState,
            navigateToStory = { navigateToStory() },
            navigateUserToStory = {
                navigateToUserStory(it)
            },
            event = event,
            onCommentClicked = { onCommentClicked(it) },
            currentUserId = currentUserId,
            navigateToMessages = { navigateToMessages() }
        )


    }
}
