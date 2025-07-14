package com.matin.noora.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.matin.noora.R
import com.matin.noora.core.domain.model.UserScore
import com.matin.noora.designsystem.NooraTheme
import com.matin.noora.feature.chat.UserScoreUiState

@Composable
fun NooraTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    userScore: UserScoreUiState
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        Spacer(Modifier.weight(1f))
        UserScoreCard(userScore = userScore)
    }
}

@Composable
fun UserScoreCard(userScore: UserScoreUiState) {
    val scores = (userScore as? UserScoreUiState.Success)?.userScore ?: UserScore(0, 0, 0)
    val enabled = userScore is UserScoreUiState.Success
    Card(
        modifier = Modifier.padding(8.dp),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ScoreItem(
                icon = R.drawable.ic_streak,
                score = scores.streak,
                isEnable = enabled,
                contentDescription = "Streak Score"
            )
            ScoreItem(
                icon = R.drawable.ic_heart,
                score = scores.like,
                isEnable = enabled,
                contentDescription = "Like Score"
            )
            ScoreItem(
                icon = R.drawable.ic_setting,
                score = scores.badge,
                isEnable = enabled,
                contentDescription = "Badge Score"
            )
        }
    }
}

@Composable
fun ScoreItem(
    @DrawableRes icon: Int,
    score: Int = 0,
    isEnable: Boolean = true,
    contentDescription: String?,
    iconSize: Dp = 24.dp,
    spacing: Dp = 2.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    modifier: Modifier = Modifier
) {
    val alpha = if (isEnable) 1f else 0.4f
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            modifier = modifier.size(iconSize),
            tint = if (isEnable) Color.Unspecified else MaterialTheme.colorScheme.onSurface.copy(
                alpha = alpha
            )
        )
        Spacer(modifier = Modifier.width(spacing))
        Text(
            text = score.toString(),
            style = textStyle,
            maxLines = 1
        )
    }
}

@Preview
@Composable
private fun NooraTopAppBarPreview() {
    NooraTheme {
        NooraTopAppBar(
            title = "Chat",
            userScore = UserScoreUiState.Success(
                UserScore(
                    streak = 5,
                    like = 10,
                    badge = 3
                )
            )
        )
    }
}
