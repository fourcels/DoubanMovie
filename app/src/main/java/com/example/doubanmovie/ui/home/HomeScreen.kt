package com.example.doubanmovie.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.doubanmovie.R
import com.example.doubanmovie.ui.movie.MovieScreen
import com.example.doubanmovie.ui.theme.DoubanMovieTheme

@Composable
fun HomeScreen() {
    Column {
        SearchBar()
        MovieScreen()
    }
}

@Composable
fun SearchBar() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            onClick = { }
        ) {
            Icon(Icons.Filled.Menu, "menu")
        }
        Surface(
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            color = Color.LightGray.copy(0.3f),
            contentColor = Color.Gray,
        ) {
            Row(
                modifier = Modifier.padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Outlined.Search,
                    contentDescription = null,
                )
                Text("抬头, 看树", style = MaterialTheme.typography.body2)
            }
        }
        IconButton(
            onClick = { }
        ) {
            Icon(Icons.Filled.MailOutline, "message")
        }
    }
}

@Composable
fun TabBar() {
    val items = listOf(
        "电影", "电视",
        "读书", "连载",
        "音乐", "同城"
    )
    var currentIndex by remember {
        mutableStateOf(0)
    }
    TabRow(
        selectedTabIndex = currentIndex,
        backgroundColor = MaterialTheme.colors.background
    ) {
        items.forEachIndexed { index, item ->
            Tab(
                modifier = Modifier.height(TabHeight),
                selected = currentIndex == index,
                onClick = { currentIndex = index }) {
                Text(text = item)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TabBarPreview() {
    DoubanMovieTheme {
        TabBar()
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    DoubanMovieTheme {
        SearchBar()
    }
}

private val TabHeight = 48.dp