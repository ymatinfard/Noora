package com.matin.noora.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.matin.noora.core.common.getAvatar
import com.matin.noora.core.domain.model.ChatCharacter
import com.matin.noora.designsystem.formatInstantToDate
import com.matin.noora.feature.chat.capitalizeFirstLetter
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun ChatCharacterRow(
    character: ChatCharacter,
    message: String,
    date: Instant? = null,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier.size(64.dp),
            painter = painterResource(id = character.getAvatar()),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .weight(.1f)
                .padding(horizontal = 6.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                character.name.capitalizeFirstLetter(),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        if (date != null) {
            Text(
                text = date.formatInstantToDate(),
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
