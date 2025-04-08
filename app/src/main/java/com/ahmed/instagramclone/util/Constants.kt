package com.ahmed.instagramclone.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object Constants {

    const val USER_COLLECTION = "Instagram_user"
    const val CHAT_REF = "chats"
}
fun Long.toFormattedTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.US)
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(Date(this))
}