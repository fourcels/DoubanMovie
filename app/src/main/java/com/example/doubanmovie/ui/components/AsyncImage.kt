package com.example.doubanmovie.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.doubanmovie.R
import com.example.doubanmovie.ui.theme.DoubanMovieTheme

@Composable
fun AsyncImage(
    modifier: Modifier = Modifier,
    model: Any,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    AsyncImage(
        modifier = modifier,
        model = model,
        contentDescription = contentDescription,
        contentScale = contentScale,
        placeholder = painterResource(id = R.drawable.ic_movie_subjectcover_default),
        fallback = painterResource(id = R.drawable.ic_movie_subjectcover_default),
        error = painterResource(R.drawable.ic_movie_subjectcover_default),
    )
}


@Preview(showBackground = true)
@Composable
fun ImagePreview() {
    DoubanMovieTheme {
        Column {
            AsyncImage(
                model = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp"
            )
        }
    }
}