package com.matin.noora.core.common

import com.matin.noora.R

val avatarMap =
    mapOf(
        "games" to R.drawable.ali,
        "language" to R.drawable.amir,
        "friend" to R.drawable.fatemeh,
        "adult" to R.drawable.majid,
        "trainer" to R.drawable.matin,
        "noora" to R.drawable.noora,
        "teacher" to R.drawable.mehran,
        "legal" to R.drawable.shahin,
        "marketing" to R.drawable.shiva,
    )

fun String.getAvatar() = avatarMap[this.lowercase()] ?: R.drawable.noora