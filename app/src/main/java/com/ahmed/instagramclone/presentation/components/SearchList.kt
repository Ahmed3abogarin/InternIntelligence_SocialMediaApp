package com.ahmed.instagramclone.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.ui.theme.InstagramCloneTheme


@Composable
fun SearchList(users: List<User>, navigateToUser: (User) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        item {
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
        }
        items(users) {
            SearchCard(it, navigateToUser = { navigateToUser(it) })
        }
    }
}


@Composable
fun SearchCard(user: User, textColor: Color = Color.Black, navigateToUser: () -> Unit) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToUser()
            }
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            if (user.imagePath.isEmpty()) {
                Image(
                    modifier = Modifier
                        .size(65.dp)
                        .clip(CircleShape),
                    painter = painterResource(R.drawable.profile_placeholder),
                    contentDescription = null
                )
            }else{
                AsyncImage(
                    model = ImageRequest.Builder(context).data(user.imagePath).build(),
                    modifier = Modifier
                        .size(65.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    contentDescription = "user image"
                )
            }




            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = user.firstName + " " + user.lastName,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )
                Text(
                    text = user.firstName + " " + user.lastName,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    InstagramCloneTheme {
        SearchList(listOf(User(firstName = "Ahmed", lastName = "Address")), navigateToUser = {})
    }
}