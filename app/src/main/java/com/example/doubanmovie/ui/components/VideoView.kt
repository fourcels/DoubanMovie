package com.example.doubanmovie.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayCircle
import androidx.compose.material.icons.outlined.PlayCircleFilled
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VideoView(
    modifier: Modifier = Modifier,
    coverUrl: String,
    content: @Composable BoxScope.() -> Unit = {},
) {
    Box(modifier = modifier) {
        AsyncImage(modifier = Modifier.fillMaxSize(), model = coverUrl)
        Icon(
            modifier = Modifier
                .align(Alignment.Center)
                .size(32.dp),
            imageVector = Icons.Outlined.PlayCircle,
            contentDescription = null,
        )
        content()
    }
}