package com.matin.noora.designsystem

import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.toJavaInstant

@OptIn(ExperimentalTime::class)
fun Instant.formatInstantToDate(): String {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        .withZone(ZoneId.systemDefault()) // or use ZoneId.of("UTC") if needed
    return formatter.format(toJavaInstant())
}