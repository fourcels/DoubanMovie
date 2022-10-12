package com.example.doubanmovie.ui.movie

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.doubanmovie.R
import com.example.doubanmovie.data.MovieItem
import com.example.doubanmovie.ui.components.RatingBar
import com.example.doubanmovie.ui.theme.DoubanMovieTheme

@Composable
fun MovieScreen() {
    Column {

    }
}

@Composable
fun TabList() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        tabList.forEach { item ->
            TabItem(modifier = Modifier.weight(1f), item = item)
        }
    }
}

@Composable
fun TabItem(modifier: Modifier = Modifier, item: Item, onClick: (Item) -> Unit = {}) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.background
        ),
        shape = RoundedCornerShape(20),
        onClick = { /*TODO*/ }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(item.icon), null)
            Text(text = item.name, modifier = Modifier.alpha(ContentAlpha.medium))
        }
    }
}

@Composable
fun HitTheaters() {
    Column {
        HitHeader(
            modifier = Modifier.padding(PaddingValues(vertical = 16.dp))
        )
        HitBody()
    }
}

@Composable
fun HitHeader(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        HitTabbar()
        Spacer(modifier = Modifier.weight(1f))
        ShowMore()
    }
}

@Composable
fun HitBody() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(movieList) { item ->
            Column {
                AsyncImage(
                    model = item.image,
                    placeholder = painterResource(id = R.drawable.ic_movie_subjectcover_default),
                    fallback = painterResource(id = R.drawable.ic_movie_subjectcover_default),
                    error = painterResource(R.drawable.ic_movie_subjectcover_default),
                    contentDescription = null,
                    modifier = Modifier
                        .width(150.dp)
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.name, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                if (item.rating != null) {
                    RatingBar(rating = item.rating)
                } else {
                    Text(text = "暂无评分", modifier = Modifier.alpha(ContentAlpha.medium))
                }
            }
        }
    }
}


@Composable
fun ShowMore() {
    var total = 33
    Row {
        Text(text = "全部$total")
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null
        )
    }
}

@Composable
fun HitTabbar() {
    var currentIndex by remember {
        mutableStateOf(0)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        hitList.forEachIndexed { index, item ->
            if (index > 0) {
                Divider(
                    modifier = Modifier
                        .padding(PaddingValues(horizontal = 16.dp))
                        .height(24.dp)
                        .width(1.dp)
                )
            }
            Tab(
                modifier = Modifier.width(IntrinsicSize.Max),
                selected = currentIndex == index,
                onClick = {
                    currentIndex = index
                }) {

                Text(
                    text = item,
                    fontSize = 24.sp,
                )
            }
        }
    }
}

@Composable
fun ComingMovie() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        comingMovieList.forEach { item ->
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                modifier = Modifier.weight(1f)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = item.title, style = MaterialTheme.typography.h6)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Outlined.KeyboardArrowRight,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = item.desc,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.alpha(ContentAlpha.medium),
                        )

                        Spacer(modifier = Modifier.weight(1f))
                        AsyncImage(
                            model = item.image,
                            placeholder = painterResource(id = R.drawable.ic_movie_subjectcover_default),
                            fallback = painterResource(id = R.drawable.ic_movie_subjectcover_default),
                            error = painterResource(R.drawable.ic_movie_subjectcover_default),
                            contentDescription = null,
                            modifier = Modifier
                                .width(75.dp)
                                .height(65.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 420)
@Composable
fun ComingMoviePreview() {
    DoubanMovieTheme {
        ComingMovie()
    }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
fun HitTheatersPreview() {
    DoubanMovieTheme {
        HitTheaters()
    }
}


@Preview(showBackground = true, widthDp = 420)
@Composable
fun TabListPreview() {
    DoubanMovieTheme {
        TabList()
    }
}

data class ComingMovieItem(
    val title: String,
    val image: String,
    val desc: String,
)

private val comingMovieList = listOf(
    ComingMovieItem(
        title = "国内即将上映",
        image = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2880320460.webp",
        desc = "近期有6部\n电影即将上映",
    ),
    ComingMovieItem(
        title = "全球值得期待",
        image = "https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2881299984.webp",
        desc = "近期有12部\n热门电影",
    ),
)


@Immutable
data class Item(
    val name: String,
    @DrawableRes val icon: Int,
)

private val tabList = listOf(
    Item("找电影", R.drawable.ic_subjects_categories_movie),
    Item("豆瓣榜单", R.drawable.ic_subjects_ranking),
    Item("即将上映", R.drawable.ic_subjects_time),
    Item("豆瓣片单", R.drawable.ic_subjects_movie_lists),
)

private val hitList = listOf("影院热映", "豆瓣热门")

private val movieList = listOf(
    MovieItem(
        "万里归途",
        "https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2881176356.webp",
        rating = 7.4f
    ),
    MovieItem(
        "还是觉得你最好",
        "https://img2.doubanio.com/view/photo/m_ratio_poster/public/p2879301401.webp",
        rating = 7.6f
    ),
    MovieItem(
        "妈妈！",
        "https://img2.doubanio.com/view/photo/m_ratio_poster/public/p2879572001.webp",
        rating = 7.4f
    ),
    MovieItem(
        "平凡英雄",
        "https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2880994870.webp",
        rating = 6.1f
    ),
    MovieItem(
        "搜救",
        "https://img3.doubanio.com/view/photo/m_ratio_poster/public/p2881182580.webp",
        rating = null
    ),
    MovieItem(
        "哥，你好",
        "https://img2.doubanio.com/view/photo/m_ratio_poster/public/p2880026973.webp",
        rating = 5.3f
    ),
    MovieItem(
        "独行月球",
        "https://img1.doubanio.com/view/photo/m_ratio_poster/public/p2876409008.webp",
        rating = 6.8f
    ),
    MovieItem(
        "狼群",
        "https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2879572474.webp",
        rating = 5.6f
    ),
    MovieItem(
        "钢铁意志",
        "https://img2.doubanio.com/view/photo/m_ratio_poster/public/p2880944813.webp",
        rating = null
    ),
    MovieItem(
        "海的尽头是草原",
        "https://img1.doubanio.com/view/photo/m_ratio_poster/public/p2877827228.webp",
        rating = 7.2f
    ),
)