package com.matin.noora.core.common

import com.matin.noora.R
import com.matin.noora.core.domain.model.ChatCharacter

fun ChatCharacter.getAvatar(): Int {
    return when (this) {
        ChatCharacter.ALI -> R.drawable.ali
        ChatCharacter.AMIR -> R.drawable.amir
        ChatCharacter.FATEMEH -> R.drawable.fatemeh
        ChatCharacter.MAJID -> R.drawable.majid
        ChatCharacter.MATIN -> R.drawable.matin
        ChatCharacter.NOORA -> R.drawable.noora
        ChatCharacter.MEHRAN -> R.drawable.mehran
        ChatCharacter.SHAHIN -> R.drawable.shahin
        ChatCharacter.SHIVA -> R.drawable.shiva
        else -> R.drawable.noora // Default avatar
    }
}