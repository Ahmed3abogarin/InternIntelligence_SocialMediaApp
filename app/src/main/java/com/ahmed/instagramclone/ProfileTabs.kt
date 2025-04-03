package com.ahmed.instagramclone



enum class ProfileTabs(
    val selectedIcon: Int,
    val unselectedIcon: Int
){
    Posts(
        unselectedIcon = R.drawable.ic_grid_outlined,
        selectedIcon = R.drawable.ic_grid_filled,
    ),
    Reels(
        unselectedIcon =R.drawable.ic_reels ,
        selectedIcon = R.drawable.ic_reels_filled,
    ),
    Tags(
        unselectedIcon = R.drawable.ic_tags_outlined,
        selectedIcon = R.drawable.ic_tags_filled,
    )
}