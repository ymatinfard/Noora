package com.matin.noora.core.domain.model


@JvmInline
value class Prompt(val value: String) {
    init {
        require(value.length <= 1000) { "Prompt cannot exceed 1000 characters" }
    }

    override fun toString(): String = value

    companion object {
        val Empty = Prompt("")
    }
}
