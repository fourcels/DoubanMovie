package com.example.doubanmovie.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.doubanmovie.ui.theme.DoubanMovieTheme

@Composable
fun DetailScreen(
    primaryColor: Color,
    onBackPressed: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("电影") },
                navigationIcon = {
                    IconButton(onClick = {
                        onBackPressed()
                    }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
                backgroundColor = primaryColor,
                contentColor = Color.White,
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.MoreHoriz, contentDescription = null)
                    }
                }
            )
        },
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(text = "detail")
        }
    }
}


@Preview(showBackground = true, widthDp = 420)
@Composable
fun DetailScreenPreview() {
    DoubanMovieTheme {
        DetailScreen(primaryColor = Color(0xFF723b2c))
    }
}