package com.ahmed.instagramclone.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.PersonAddAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.instagramclone.ProfileTabs
import com.ahmed.instagramclone.R
import com.ahmed.instagramclone.ui.theme.InstagramCloneTheme
import kotlinx.coroutines.launch


@Composable
fun ProfileScreen() {
    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = { ProfileTabs.entries.size })
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(8.dp)
    ) {
        Text(
            text = "Ahmed_Adil",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        Spacer(modifier = Modifier.height(18.dp))
        Row {
            Box(
                modifier = Modifier
                    .size(91.dp)
            ) {
                Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(90.dp)
                        .clip(CircleShape)
                        .background(Color.Black),
                    painter = painterResource(R.drawable.ic_launcher_background),
                    contentDescription = null
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
            Spacer(modifier = Modifier.width(22.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "0",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(text = "posts", style = MaterialTheme.typography.titleMedium)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "35",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(text = "followers", style = MaterialTheme.typography.titleMedium)
                }
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "90",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Text(text = "following", style = MaterialTheme.typography.titleMedium)
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {},
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text(text = "Edit profile")
            }
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = {},
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black)
            ) {
                Text(text = "Share profile")
            }

            OutlinedButton(
                modifier = Modifier.weight(0.25f),
                onClick = {},
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(width = 1.dp, color = Color.Black),
                contentPadding = PaddingValues(0.dp)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black,
                    imageVector = Icons.Default.PersonAddAlt,
                    contentDescription = null
                )
            }

        }

        Spacer(modifier = Modifier.height(16.dp))
        TabRow(
            indicator = { tabPositions ->
                SecondaryIndicator(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[selectedTabIndex.value])
                        .fillMaxWidth(0.2f)
                        .background(Color.Red, shape = RoundedCornerShape(50)),
                    color = Color.Red
                )
            },
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth(),

        ) {
            ProfileTabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex.value == index,
                    selectedContentColor = Color.Black,
                    unselectedContentColor = Color.Black,
                    onClick = {
                        scope.launch { pagerState.animateScrollToPage(currentTab.ordinal) }
                    },
                    icon = {
                        Icon(
                            modifier = Modifier.size(28.dp),
                            painter = painterResource(
                                if (selectedTabIndex.value == index)
                                    currentTab.selectedIcon else currentTab.unselectedIcon
                            ),
                            contentDescription = null
                        )
                    }
                )

            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().weight(1f)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                val pageText = when(it){
                    0 -> "Your posts"
                    1 -> "Your reels"
                    2 -> "Your tags"
                    else -> ""
                }
                Text(text = pageText )
            }

        }

    }
}


@Preview(showBackground = true)
@Composable
fun SettingsPreview() {
    InstagramCloneTheme {
        ProfileScreen()
    }
}