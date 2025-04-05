package com.ahmed.instagramclone.presentation.components

import android.content.ClipData.Item
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.ui.theme.InstagramCloneTheme





@Composable
fun SearchList(users: List<User>){
    LazyColumn (
        modifier = Modifier.padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ){
        item{
            HorizontalDivider(modifier = Modifier.fillMaxSize().padding(bottom = 4.dp))
        }
        items(users) {
            SearchCard(it)
        }
    }
}


@Composable
fun SearchCard(user: User){
    val context = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
        Row {

            AsyncImage(
                model = ImageRequest.Builder(context).data(user.imagePath).build(),
                modifier = Modifier.size(65.dp).clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = "user image"
            )


            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = user.firstName + "" + user.lastName, fontWeight = FontWeight.SemiBold)
                Text(text =  user.firstName + "" + user.lastName, fontSize = 13.sp, color = Color.Gray)
            }
        }

        IconButton(onClick = {}) {
            Icon(Icons.Default.Close, tint = Color.Gray, contentDescription = null)
        }


    }

}


@Preview(showBackground = true)
@Composable
fun SearchPreview(){
    InstagramCloneTheme {
        SearchList(listOf(User()))
    }
}