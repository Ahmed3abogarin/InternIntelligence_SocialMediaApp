package com.ahmed.instagramclone.presentation.search

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.presentation.components.AppSearchBar
import com.ahmed.instagramclone.presentation.components.SearchList
import com.ahmed.instagramclone.util.Resource


@Composable
fun SearchScreen(
    state: Resource<List<User>>?,
    event: (SearchEvent) -> Unit,
    navigateToUp: () -> Unit,
) {
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            IconButton(onClick = { navigateToUp() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }

            AppSearchBar(
                modifier = Modifier.padding(start = 6.dp),
                text = text,
                onValueChange = {
                    text = it
                    event(SearchEvent.UpdateSearchQuery(text))
                },
                onSearch = { event(SearchEvent.SearchNews) }
            )

        }
        Spacer(modifier = Modifier.height(6.dp))
        when (state) {
            is Resource.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }

            is Resource.Success -> {
                state.data?.let {
                    SearchList(it)
                }
            }

            is Resource.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }
}

