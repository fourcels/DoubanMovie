package com.example.doubanmovie.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.doubanmovie.ui.theme.DoubanMovieTheme

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Float,
    max: Float = 10f,
    numberOfStars: Int = 5,
    contentDescription: String = "rating",
    starIcon: ImageVector = Icons.Filled.Star,
    ratedStarsColor: Color = MaterialTheme.colors.secondary,
    unRatedStarsColor: Color = Color.LightGray,
    spaceBetween: Dp = 0.dp
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spaceBetween),
        ) {
            for (star in 1..numberOfStars) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = starIcon, contentDescription = contentDescription,
                    tint = if (star <= rating / (max / numberOfStars)) ratedStarsColor else unRatedStarsColor
                )
            }
        }
        Text(
            text = rating.toString(),
            color = ratedStarsColor,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
    DoubanMovieTheme {
        RatingBar(rating = 3f)
    }
}