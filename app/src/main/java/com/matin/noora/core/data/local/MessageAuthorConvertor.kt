package com.matin.noora.core.data.local

import androidx.room.TypeConverter
import com.matin.noora.core.domain.model.MessageAuthor

class MessageAuthorConvertor {

    @TypeConverter
    fun from(author: MessageAuthor): Int {
        return MessageAuthor.entries.indexOf(author)
    }

    @TypeConverter
    fun to(index: Int): MessageAuthor {
        return MessageAuthor.entries[index]
    }
}