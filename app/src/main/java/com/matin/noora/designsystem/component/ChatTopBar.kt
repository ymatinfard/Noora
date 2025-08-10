package com.matin.noora.designsystem.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.matin.noora.R
import com.matin.noora.designsystem.NooraIcons

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ChatTopBar(
    userName: String = "",
    subtitle: String = "online",
    onBackClick: () -> Unit = {},
    onSearchCloseClick: () -> Unit = {},
    profileImageRes: Int = R.drawable.noora,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
) {
    var isSearchEnabled by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(isSearchEnabled) {
        if (isSearchEnabled) {
            focusRequester.requestFocus()
        }
    }

    CenterAlignedTopAppBar(
        title = {
            AnimatedContent(isSearchEnabled, label = "") { isSearching ->
                if (isSearching) {
                    SearchBar(
                        query = query,
                        onQueryChange = onQueryChange,
                        onCloseClick = {
                            isSearchEnabled = false
                            focusRequester.freeFocus()
                            onSearchCloseClick()
                        },
                        focusRequester = focusRequester,
                    )
                } else {
                    ChatTitle(title = userName, subtitle = subtitle)
                }
            }
        },
        actions = {
            if (!isSearchEnabled) {
                ChatActions(
                    onSearchClick = {
                        isSearchEnabled = true
                    })
            }
        },
        navigationIcon = {
            if (!isSearchEnabled) {
                ChatNavigationIcon(
                    onBackClick = onBackClick,
                    profileImageRes = profileImageRes
                )
            }
        }
    )
}

@Composable
private fun ChatTitle(title: String, subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append(title)
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append(" ")
                    append(subtitle)
                }
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
private fun ChatActions(
    onSearchClick: () -> Unit,
) {
    Row(
        modifier = Modifier.padding(end = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector = NooraIcons.SEARCH,
                contentDescription = "search",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Composable
private fun ChatNavigationIcon(
    onBackClick: () -> Unit,
    profileImageRes: Int
) {
    Row(
        modifier = Modifier.padding(start = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(
                imageVector = NooraIcons.ARROW_BACK,
                contentDescription = "navigate back"
            )
        }

        Image(
            painter = painterResource(profileImageRes),
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp),
            contentDescription = "profile image"
        )
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onQueryChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    focusRequester: FocusRequester
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(end = 8.dp),
            imageVector = NooraIcons.SEARCH,
            contentDescription = "search"
        )
        TextField(
            modifier = modifier
                .weight(1f)
                .focusRequester(focusRequester),
            value = query,
            onValueChange = onQueryChange,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            singleLine = true,
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
            ),
        )
        IconButton(
            onClick = onCloseClick
        ) {
            Icon(
                modifier = Modifier.padding(start = 8.dp),
                imageVector = Icons.Default.Close,
                contentDescription = "close search"
            )
        }
    }
}

@Preview
@Composable
private fun SearchBarPreview() {
    SearchBar(
        modifier = Modifier,
        query = "",
        onQueryChange = {},
        onCloseClick = {},
        focusRequester = remember { FocusRequester() }
    )
}

@Preview
@Composable
fun ChatBarPreview(modifier: Modifier = Modifier) {
  // ChatTopBar()
}
