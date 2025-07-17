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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.matin.noora.R
import com.matin.noora.core.common.getAvatar
import com.matin.noora.core.domain.model.ChatCharacterItem
import com.matin.noora.core.domain.model.Tool
import com.matin.noora.designsystem.NooraTheme
import com.matin.noora.feature.chat.ChatCharactersState
import com.matin.noora.feature.chat.capitalizeFirstLetter
import kotlin.time.ExperimentalTime

@Composable
fun HomeScreenRoute(viewModel: HomeScreenViewModel = hiltViewModel()) {
    val charactersState by viewModel.characters.collectAsStateWithLifecycle()
    val toolsState by viewModel.tools.collectAsStateWithLifecycle()
    HomeScreen(charactersState = charactersState, toolsState, viewModel::onToolClicked)
}

@Composable
fun HomeScreen(
    charactersState: ChatCharactersState,
    toolsState: ToolsState,
    onToolClicked: (Tool) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        HomeTopBar("USF")
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            SectionSpacer()
            HelpCard()
            SectionSpacer()
            CharactersCard(charactersState)
            SectionSpacer()
            ToolsCard(toolsState, onToolClicked)
        }
    }
}

@Composable
private fun SectionSpacer() {
    Spacer(modifier = Modifier.padding(vertical = 8.dp))
}

@Composable
fun ToolsCard(toolsState: ToolsState, onToolClicked: (Tool) -> Unit = {}) {
    when (toolsState) {
        is ToolsState.Loading -> {
            Text(
                modifier = Modifier.padding(16.dp),
                text = stringResource(R.string.loading),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        is ToolsState.Error -> {
            Text(
                modifier = Modifier.padding(16.dp),
                text = toolsState.message,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        is ToolsState.Success -> {
            ToolsCardContent(toolsState.tools, onToolClicked = onToolClicked)
        }
    }
}

@Composable
fun ToolsCardContent(
    tools: List<Tool>,
    onToolClicked: (Tool) -> Unit,
    onSeeMoreClicked: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        SeeMore("Tools", onSeeMoreClicked)
        LazyVerticalGrid(
            modifier = Modifier.heightIn(max = 500.dp),
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = false,
        ) {
            items(tools) { tool ->
                ToolItem(tool, onToolClicked = { onToolClicked(tool) })
            }
        }
    }
}

@Composable
private fun ToolItem(tool: Tool, onToolClicked: (Tool) -> Unit = {}) {
    Card(
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                modifier = Modifier.size(120.dp),
                painter = painterResource(id = tool.imgRes),
                contentDescription = tool.name,
            )
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                text = tool.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = tool.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outlineVariant,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
            )
        }
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        SeeMore("Characters", onSeeMoreClicked)
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(
                modifier = Modifier.padding(vertical = 16.dp),
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
private fun SeeMore(title: String, onSeeMoreClicked: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = title,
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
            ),
            toolsState = ToolsState.Success(
                tools = listOf(
                    Tool(
                        "Tool 1",
                        "Description of Tool 1",
                        description = "",
                        iconUrl = "",
                        imgRes = R.drawable.ic_summarize
                    ),
                    Tool(
                        "Tool 2",
                        "Description of Tool 2",
                        description = "",
                        iconUrl = "",
                        imgRes = R.drawable.ic_drawing
                    ),
                )
            ),
            onToolClicked = {}
        )
    }
}

@Preview
@Composable
private fun ToolsCardPreview() {
    NooraTheme {
        ToolsCard(
            toolsState = ToolsState.Success(
                tools = listOf(
                    Tool(
                        "Image creation",
                        "Tool 1",
                        description = "Summarize your text, no worries how content is long or complex just drop it here!",
                        iconUrl = "",
                        imgRes = R.drawable.ic_summarize
                    ),
                    Tool(
                        "Summarize",
                        "Tool 2",
                        description = "You can draw anything you want",
                        iconUrl = "",
                        imgRes = R.drawable.ic_drawing
                    ),
                    Tool(
                        "Maths ",
                        "Maths",
                        description = "You can draw anything you want",
                        iconUrl = "",
                        imgRes = R.drawable.ic_math
                    ),
                    Tool(
                        "id b1 ",
                        "Writing",
                        description = "You can writing anything you want",
                        iconUrl = "",
                        imgRes = R.drawable.ic_writing
                    ),
                )
            )
        )
    }

}