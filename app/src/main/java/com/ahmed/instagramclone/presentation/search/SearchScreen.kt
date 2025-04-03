package com.ahmed.instagramclone.presentation.search

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
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
import com.ahmed.instagramclone.util.Resource


@Composable
fun SearchScreen(state: Resource<List<User>>?, event: (SearchEvent) -> Unit) {
    val context = LocalContext.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by remember { mutableStateOf("") }

        AppSearchBar(
            modifier = Modifier.padding(horizontal = 14.dp),
            text = text,
            onValueChange = {
                text = it
                event(SearchEvent.UpdateSearchQuery(text))
            },
            onSearch = { event(SearchEvent.SearchNews) }
        )

        when(state){
            is Resource.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()

            }

            is Resource.Success -> {
                state.data?.let {
                    LazyColumn {
                        items(state.data) {
                            Text(text = "user ${it.firstName}")
                        }
                    }
                }
            }

            is Resource.Error -> {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

            else -> Unit
        }
    }
}

