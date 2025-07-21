package com.matin.noora.core.data.local

import androidx.room.TypeConverter
import com.matin.noora.core.domain.model.MessageState

class MessageStateConverter {

    @TypeConverter
    fun fromMessageState(state: MessageState): String {
        return state.name
    }

    @TypeConverter
    fun toMessageState(stateName: String): MessageState {
        return MessageState.valueOf(stateName)
    }
}