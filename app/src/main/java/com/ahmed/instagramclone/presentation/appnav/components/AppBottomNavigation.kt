package com.ahmed.instagramclone.presentation.appnav.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ahmed.instagramclone.presentation.appnav.BottomNavigationItem
import com.ahmed.instagramclone.util.Dimens.IconSize
import kotlinx.coroutines.delay

@Composable
fun AppBottomNavigationRegular(
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClicked: (Int) -> Unit,
) {

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = index == selected,
                onClick = { onItemClicked(index) },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = null,
                            modifier = Modifier.size(IconSize)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Black,
                    unselectedTextColor = Color.Black,
                    indicatorColor = MaterialTheme.colorScheme.background
                )
            )
        }

    }

}


@Composable
fun AppBottomNavigation(
    items: MutableList<BottomNavigationItem>,
    selectedIndex: Int,
    onItemClicked: (Int) -> Unit,
) {
    val configuration = LocalConfiguration.current

    val indicatorWidth = (configuration.screenWidthDp/items.count())/2

    val indicatorOffset by animateIntOffsetAsState(
        targetValue = IntOffset(
            items[selectedIndex].offset.x.toInt() + (items[selectedIndex].size.width / 4) - (items.count()*2)+(-2),
            15
        ), animationSpec = tween(400)
    )
    val indicatorColor by animateColorAsState(
        targetValue = items[selectedIndex].color,
        animationSpec = tween(500)
    )

    val switching = remember {
        mutableStateOf(false)
    }
    LaunchedEffect(switching.value) {
        if (switching.value) {
            delay(250)
            switching.value = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(8.dp, RoundedCornerShape(10.dp))
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(Color.White)
    ) {
        HorizontalDivider(modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed{ index, item ->
                Box(
                    modifier = Modifier
                        .onGloballyPositioned {
                            val offset = it.positionInParent()
                            items[index] =
                                items[index].copy(
                                    offset = offset,
                                    size = it.size
                                )
                        }
                        .weight((1.0 / items.count()).toFloat())
                        .clickable(
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            onClick = {
                                switching.value = true
                                onItemClicked(index)
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(IconSize),
                        painter = painterResource(item.icon),
                        contentDescription = null,
                        tint = if(selectedIndex == index) indicatorColor else Color.Black
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .offset {
                    indicatorOffset
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .shadow(
                        2.dp,
                        CircleShape,
                        ambientColor = indicatorColor,
                        spotColor = indicatorColor
                    )
                    .height(3.dp)
                    .width(indicatorWidth.dp)
                    .clip(CircleShape)
                    .background(indicatorColor)
            )
            AnimatedVisibility(
                visible = !switching.value,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                )
            }
        }
    }
}



