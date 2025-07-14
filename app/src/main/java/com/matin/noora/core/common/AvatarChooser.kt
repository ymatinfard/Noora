package com.matin.noora.core.common

import com.matin.noora.R

fun String.getAvatar(): Int {
    return when (this) {
        "ali" -> R.drawable.ali
        "amir" -> R.drawable.amir
        "fatemeh" -> R.drawable.fatemeh
        "majid" -> R.drawable.majid
        "matin" -> R.drawable.matin
        "noora" -> R.drawable.noora
        "mehran" -> R.drawable.mehran
        "shahin" -> R.drawable.shahin
        "shiva" -> R.drawable.shiva
        else -> R.drawable.noora // Default avatar
    }
}