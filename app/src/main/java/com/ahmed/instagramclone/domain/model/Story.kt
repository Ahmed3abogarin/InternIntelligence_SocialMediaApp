package com.ahmed.instagramclone.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Story(
    val authorId: String = "",
    val username: String = "",
    val videoUrl: String = ""
): Parcelable
