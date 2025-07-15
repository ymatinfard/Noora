package com.matin.noora.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.noora.R
import com.matin.noora.core.common.getAvatar
import com.matin.noora.core.domain.model.ChatCharacterItem
import com.matin.noora.designsystem.NooraTheme
import com.matin.noora.feature.chat.ChatCharactersState
import com.matin.noora.feature.chat.capitalizeFirstLetter
import kotlin.time.ExperimentalTime

@Composable
fun HomeScreenRoute(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val charactersState by viewModel.characters.collectAsStateWithLifecycle()
    HomeScreen(charactersState = charactersState)
}

@Composable
fun HomeScreen(charactersState: ChatCharactersState) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        HomeTopBar("USF")
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        HelpCard()
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        CharactersCard(charactersState)
    }
}

@Composable
fun CharactersCard(
    charactersStat: ChatCharactersState,
    onCharacterClicked: (ChatCharacterItem) -> Unit = {},
    onSeeMoreClicked: () -> Unit = {}
) {
    when (charactersStat) {
        is ChatCharactersState.Loading -> {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.loading),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        is ChatCharactersState.Error -> {
            Text(
                modifier = Modifier.padding(16.dp),
                text = charactersStat.message,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        is ChatCharactersState.Success -> {
            CharacterCardContent(
                charactersStat.characters,
                onSeeMoreClicked,
                onCharacterClicked,
            )
        }
    }
}

@Composable
private fun CharacterCardContent(
    characters: List<ChatCharacterItem>,
    onSeeMoreClicked: () -> Unit,
    onCharacterClicked: (ChatCharacterItem) -> Unit
) {
    Card{
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = stringResource(R.string.characters),
                    style = MaterialTheme.typography.titleLarge,
                )
                TextButton(
                    onClick = onSeeMoreClicked,
                ) {
                    Text(
                        modifier = Modifier.clickable {},
                        text = stringResource(R.string.see_more),
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                contentPadding = PaddingValues(start = 16.dp)
            ) {
                items(
                    characters
                ) { character ->
                    CharacterItem(name = character.name) {
                        onCharacterClicked(character)
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(name: String, onClick: () -> Unit = {}) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Image(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape),
            painter = painterResource(id = name.getAvatar()),
            contentDescription = "Character Avatar"
        )
        Text(
            text = name.capitalizeFirstLetter(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 6.dp)
        )
    }
}

@Composable
fun HelpCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.noora),
                    contentDescription = "Help Icon",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = CircleShape
                        )
                )
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = stringResource(R.string.what_can_i_help_you_with),
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.message),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center,

                    ) {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(6.dp)
                            .rotate(-45f),
                        tint = MaterialTheme.colorScheme.onPrimary,
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send Icon",
                    )
                }
            }
        }
    }
}

@Composable
fun HomeTopBar(name: String, onProfileClicked: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ProfileName(name)
        Spacer(modifier = Modifier.weight(1f))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InviteFriendIcon()
            ProfileIcon(name)
        }
    }
}

@Composable
private fun ProfileName(name: String) {
    Text("Hi, $name!", style = MaterialTheme.typography.headlineSmall)
}

@Composable
private fun InviteFriendIcon() {
    Box(
        modifier = Modifier
            .size(32.dp)
            .clip(shape = CircleShape)
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_gift),
            contentDescription = "Profile Icon",
            tint = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Composable
fun ProfileIcon(name: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(32.dp)
            .clip(shape = CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            ),
    ) {
        Text(
            text = name.first().toString(), color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@OptIn(ExperimentalTime::class)
@Preview(showBackground = true)
@Composable
private fun HomeTopBarPreview() {
    NooraTheme {
        // HomeTopBar(name = "Ali", onProfileClicked = {})
        CharactersCard(
            charactersStat = ChatCharactersState.Success(
                characters = listOf(
                    ChatCharacterItem("1", "ali", "A friendly character"),
                    ChatCharacterItem("2", "fatemeh", "A helpful character"),
                    ChatCharacterItem("3", "shiva", "A curious character"),
                    ChatCharacterItem("3", "shiva", "A curious character"),
                    ChatCharacterItem("3", "shiva", "A curious character"),
                    ChatCharacterItem("3", "shiva", "A curious character"),
                    ChatCharacterItem("3", "shiva", "A curious character"),
                    ChatCharacterItem("4", "majid", "A wise character")
                )
            ),
            onCharacterClicked = {},
            onSeeMoreClicked = { /* Handle see more click */ }
        )
    }
}

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    NooraTheme {
        HomeScreen(
            charactersState = ChatCharactersState.Success(
                characters = listOf(
                    ChatCharacterItem("1", "ali", "A friendly character"),
                    ChatCharacterItem("2", "fatemeh", "A helpful character"),
                    ChatCharacterItem("3", "shiva", "A curious character"),
                    ChatCharacterItem("4", "majid", "A wise character")
                )
            )
        )
    }
}