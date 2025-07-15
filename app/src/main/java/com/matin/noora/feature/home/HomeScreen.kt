package com.matin.noora.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.matin.noora.R
import com.matin.noora.designsystem.NooraTheme

@Composable
fun HomeScreenRoute() {

    HomeScreen()
}

@Composable
fun HomeScreen() {
    Column {
        HomeTopBar("USF")
        HelpCard()
    }
}

@Composable
fun HelpCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
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
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.message), color = MaterialTheme.colorScheme.outlineVariant)
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
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
    Text("Hi, $name!", style = MaterialTheme.typography.headlineMedium)
}

@Composable
private fun InviteFriendIcon() {
    Box(
        modifier = Modifier
            .size(48.dp)
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
        modifier = Modifier
            .size(64.dp)
            .clip(shape = CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center,

        ) {
        Text(
            text = name.first().toString(), color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.displayMedium
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeTopBarPreview() {
    NooraTheme {
        // HomeTopBar(name = "Ali", onProfileClicked = {})
        HelpCard()
    }
}