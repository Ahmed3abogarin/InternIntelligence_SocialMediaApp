package com.ahmed.instagramclone.presentation.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.ui.theme.InstagramCloneTheme

@Composable
fun Modifier.shimmerEffect() = composed {
    val transition = rememberInfiniteTransition()
    val alpha = transition.animateFloat(
        initialValue = 0.2f, targetValue = 0.9f, animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    ).value
    this.background(color = Color.LightGray.copy(alpha = alpha)) // shimmer color
}

@Composable
fun PostShimmerEffect() {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.width(6.dp))
            Column {
                Box(
                    modifier = Modifier
                        .height(5.dp)
                        .width(82.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(4.dp))

                Box(
                    modifier = Modifier
                        .height(5.dp)
                        .width(72.dp)
                        .clip(CircleShape)
                        .shimmerEffect()
                )
            }


        }

        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .height(5.dp)
                .width(240.dp)
                .clip(CircleShape)
                .padding(start = 8.dp)
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(6.dp))

        Box(
            modifier = Modifier
                .height(5.dp)
                .width(250.dp)
                .clip(CircleShape)
                .padding(start = 8.dp)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(6.dp))

    }


}


@Composable
fun ShimmerEffect() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        LazyRow(
            userScrollEnabled = false,
            modifier = Modifier.padding(start = 7.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(6) {
                Box(modifier = Modifier
                    .size(83.dp)
                    .clip(CircleShape)
                    .shimmerEffect())
            }
        }
        Spacer(modifier = Modifier.height(16.dp))


        repeat(4) {
            PostShimmerEffect()
        }

    }
}

@Preview
@Composable
fun PostShimmerPreview() {
    InstagramCloneTheme {
        ShimmerEffect()
    }
}