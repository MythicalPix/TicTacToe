package com.kleinreveche.tictactoe.presentation.common.components

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BackHistoryTopAppBar(
    modifier: Modifier = Modifier,
    text: String,
    navController: NavController,
    onClick: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = text, textAlign = TextAlign.Center) },
        modifier = modifier.background(MaterialTheme.colorScheme.surfaceTint),
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = onClick) {
                Icon(
                    Icons.Filled.History,
                    contentDescription = "History",
                )
            }
        },
    )
}
