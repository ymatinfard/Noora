package com.matin.noora.feature.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.noora.core.domain.model.UserScore
import com.matin.noora.designsystem.NooraTheme
import com.matin.noora.designsystem.component.NooraTopAppBar
import com.matin.noora.designsystem.component.UserScoreCard

@Composable
fun CharacterSelectionRoute(viewModel: ChatCharactersScreenViewModel = hiltViewModel()) {
    val userScoreState by viewModel.userScore.collectAsStateWithLifecycle()
    CharacterSelection(userScore = userScoreState)
}

@Composable
fun CharacterSelection(userScore: UserScoreUiState, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        NooraTopAppBar(title = "Chat", userScore = userScore)
    }
}

@Preview(showBackground = true)
@Composable
private fun ScoreIndicatorsPreview() {
    NooraTheme {
        UserScoreCard(
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