package com.ahmed.instagramclone.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.ahmed.instagramclone.domain.model.Comment
import com.ahmed.instagramclone.domain.model.CommentWithUser
import com.ahmed.instagramclone.domain.model.User
import com.ahmed.instagramclone.ui.theme.InstagramCloneTheme
import com.ahmed.instagramclone.util.toFormattedTime

@Composable
fun CommentCard(comment: CommentWithUser) {
    val context = LocalContext.current
    Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){

        Row {
            if (comment.user.imagePath.isEmpty()) {
                Image(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    painter = painterResource(R.drawable.profile_placeholder),
                    contentDescription = null
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(context).data(comment.user.imagePath).build(),
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.padding(end = 6.dp)) {
                Row {
                    Text(text = comment.user.firstName + " " + comment.user.lastName, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(text = comment.comment.time.toFormattedTime(), fontSize = 9.sp)
                }

                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = comment.comment.commentTxt,
                    fontSize = 12.sp
                )
            }
        }



        Icon(
            modifier = Modifier.align(Alignment.CenterVertically),
            imageVector = Icons.Default.FavoriteBorder,
            contentDescription = null
        )
    }


}

@Preview
@Composable
fun CommentCardPre() {
    InstagramCloneTheme {
        CommentCard(comment = CommentWithUser(Comment(commentTxt = "tool"), User(firstName = "Ahmed", lastName = "Adil")))
    }

}
