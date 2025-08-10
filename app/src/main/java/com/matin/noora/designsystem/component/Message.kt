package com.matin.noora.designsystem.component

import MessageTimeStamp
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matin.noora.core.domain.model.Message

private const val MESSAGE_BUBBLE_CORNER_RADIUS = 16
private const val MESSAGE_TEXT_SIZE = 20
internal const val TIMESTAMP_TEXT_SIZE = 14
private const val MESSAGE_VERTICAL_PADDING = 8

@Composable
fun MessageList(
    modifier: Modifier,
    messages: List<Message>,
    isMsgPending: Boolean,
    listState: LazyListState,
    query: String = "",
) {
    Box(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        LazyColumn(
            reverseLayout = true,
            state = listState
        ) {
            item {
                AnimatedVisibility(visible = isMsgPending) {
                    MessageBubbleContainer(isFromCurrentUser = false) {
                        LoadingPulse(
                            modifier = Modifier.padding(10.dp),
                            color = MaterialTheme.colorScheme.onPrimary,
                            size = 14.dp,
                            spaceBetween = 3.dp,
                            travelDistance = 10.dp
                        )
                    }
                }
            }
            items(
                items = messages,
                key = { it.id }
            ) { message ->
                Spacer(Modifier.height(MESSAGE_VERTICAL_PADDING.dp))
                TextMessageContent(message, query)
            }
        }
    }
}

@Composable
private fun MessageBubbleContainer(
    isFromCurrentUser: Boolean,
    content: @Composable () -> Unit,
) {
    val alignment = if (isFromCurrentUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = alignment
    ) {
        MessageBubble(isFromCurrentUser) {
            content()
        }
    }
}

@Composable
private fun MessageBubble(
    isFromCurrentUser: Boolean,
    content: @Composable () -> Unit
) {
    val shape = chooseMessageBoxShape(isFromCurrentUser, MESSAGE_BUBBLE_CORNER_RADIUS.dp)
    val backgroundColor = if (isFromCurrentUser)
        MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
    Box(
        modifier = Modifier
            .clip(shape = shape)
            .background(color = backgroundColor)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        content()
    }
}

@Composable
private fun chooseMessageBoxShape(
    isFromCurrentUser: Boolean,
    cornerRadius: Dp,
): RoundedCornerShape {
    val baseShape = RoundedCornerShape(cornerRadius)
    return if (isFromCurrentUser) {
        baseShape.copy(bottomEnd = CornerSize(0))
    } else {
        baseShape.copy(bottomStart = CornerSize(0))
    }
}

@Composable
private fun TextMessageContent(message: Message, query: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MESSAGE_VERTICAL_PADDING.dp),
    ) {
        if (message.prompt.value.isNotEmpty())
            TextMessage(message.prompt.value, message.createdAt, query, true)

        if (message.response.isNotEmpty()) {
            TextMessage(message.response, message.createdAt, query, false)
        }
    }
}

@Composable
private fun TextMessage(
    message: String,
    timestamp: Long,
    query: String,
    isFromCurrentUser: Boolean
) {

    val trimmedQuery = query.trim()
    val text = if (trimmedQuery.isNotEmpty() && message.contains(
            trimmedQuery,
            ignoreCase = true
        )
    ) {
        remember(message, trimmedQuery) {
            highlightText(message, trimmedQuery)
        }
    } else {
        message
    }

    MessageBubbleContainer(isFromCurrentUser) {
        Column(verticalArrangement = Arrangement.Bottom) {
            when (text) {
                is AnnotatedString -> {
                    Text(
                        text = text,
                        fontSize = MESSAGE_TEXT_SIZE.sp,
                        color = chooseOnSurfaceColorFor(isFromCurrentUser)
                    )
                }

                is String -> {
                    Text(
                        text = text,
                        fontSize = MESSAGE_TEXT_SIZE.sp,
                        color = chooseOnSurfaceColorFor(isFromCurrentUser)
                    )
                }
            }

            MessageTimeStamp(
                timeStamp = timestamp,
                isFromCurrentUser = isFromCurrentUser,
                modifier = Modifier.align(alignment = if (isFromCurrentUser) Alignment.End else Alignment.Start)
            )
        }
    }
}

fun highlightText(text: String, query: String): AnnotatedString {

    return buildAnnotatedString {
        var currentIndex = 0
        var matchIndex = text.indexOf(query, currentIndex)

        while (matchIndex >= 0) {
            val endIndex = matchIndex + query.length
            append(text.substring(currentIndex, matchIndex))
            withStyle(style = SpanStyle(background = Color.Yellow, fontWeight = FontWeight.Bold)) {
                append(text.substring(matchIndex, endIndex))
            }
            currentIndex = endIndex
            matchIndex = text.indexOf(query, currentIndex)
        }

        if (currentIndex < text.length) {
            append(text.substring(currentIndex))
        }
    }
}
