package com.ahmed.instagramclone.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StoryList(navigateToStory : () -> Unit){
    val myColors = listOf(
        Color.Cyan,
        Color.Yellow
//        Color(0xFFff1554)
    )
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        item {
            Column(modifier = Modifier.clickable { navigateToStory()  }) {
                Box(
                    modifier = Modifier
                        .size(80.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(77.dp)
                            .clip(CircleShape)
                            .background(Color.Black)
                    )

                    Icon(
                        Icons.Default.AddCircle, contentDescription = "", modifier = Modifier
                            .align(
                                Alignment.BottomEnd
                            )
                            .clip(CircleShape)
                            .background(Color.White)
                    )
                }
                Text(text = "Your story",fontSize = 11.sp)
            }

        }

        items(12) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(83.dp)
                        .clip(CircleShape)
                        .background(brush = Brush.sweepGradient(myColors)),
                    contentAlignment = Alignment.Center
                ){
                    Box(
                        modifier = Modifier
                            .size(77.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    )
                }

                Text(text = "Person $it", fontSize = 11.sp)
            }

        }


    }
}