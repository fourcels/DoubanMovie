package com.example.doubanmovie.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.doubanmovie.data.*
import com.example.doubanmovie.ui.components.AsyncImage
import com.example.doubanmovie.ui.theme.DoubanMovieTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBackPressed: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .background(
                Brush.verticalGradient(
                    0.1f to Color(0xFF723B2C),
                    0.4f to Color(0xFF4C271D),
                )
            ),
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Text(text = "电影")
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Outlined.MoreHoriz, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }

    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
        ) {
            item {
                Header()
            }
            item {
                DoubanRating()
            }
        }
    }
}

@Composable
fun HonoInfo(info: HonorInfo) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .height(IntrinsicSize.Min)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        0f to Color(0xFFFDD190),
                        1f to Color(0xFFFFBB50),
                    )
                )
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {

            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                text = "No.${info.rank}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF863900)
            )
        }
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        0f to Color(0xFF656159),
                        1f to Color(0xFF494B4B),
                    )
                )
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = info.title,
                    color = Color(0xFFFFBA4B),
                    style = MaterialTheme.typography.bodyMedium
                )
                Icon(
                    Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    tint = Color(0xFFFFBA4B),
                )
            }
        }
    }
}

@Composable
fun HonorInfos(honorInfos: List<HonorInfo>) {
    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        honorInfos.forEach { item ->
            HonoInfo(item)
        }
    }
}

@Composable
fun MovieDesc(
    countries: List<String>,
    genres: List<String>,
    pubdate: List<String>,
    durations: String,
) {
    val countryStr = countries.joinToString(" ")
    val genreStr = genres.joinToString(" ")
    val pubdateStr = pubdate.joinToString(" ")
    val content = listOf(
        countryStr,
        genreStr,
        pubdateStr,
        "片长${durations}"
    ).joinToString(" / ")

    val myId = "inlineContent"
    val inlineContent = mapOf(
        myId to InlineTextContent(
            placeholder = Placeholder(
                width = 1.em,
                height = 1.em,
                placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
            )
        ) {
            Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null)
        }
    )

    val text = buildAnnotatedString {
        append(content)
        // Append a placeholder string "[myBox]" and attach an annotation "inlineContent" on it.
        appendInlineContent(myId)
    }

    Text(
        text = text,
        inlineContent = inlineContent,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.alpha(0.8f)
    )
}

@Composable
fun MovieActionButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    text: String,
    onClick: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black,
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Text(text = text)
        }
    }
}

@Composable
fun MovieAction() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        MovieActionButton(Modifier.weight(1f), icon = Icons.Outlined.FavoriteBorder, text = "想看")
        MovieActionButton(Modifier.weight(1f), icon = Icons.Outlined.StarBorder, text = "看过")
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AsyncImage(
            model = movieDetail.image,
            modifier = Modifier
                .width(105.dp)
                .height(142.dp)
                .clip(RoundedCornerShape(4.dp))

        )
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = movieDetail.title, style = MaterialTheme.typography.titleLarge)
            Text(
                text = "${movieDetail.originalTitle} (${movieDetail.year})",
                style = MaterialTheme.typography.titleSmall
            )
            HonorInfos(movieDetail.honorInfos)
            MovieDesc(
                movieDetail.countries,
                movieDetail.genres,
                movieDetail.pubdate,
                movieDetail.durations,
            )
            MovieAction()
        }

    }
}

@Composable
fun RatingScore(
    rating: Rating,
    numberOfStars: Int = 5,
    contentDescription: String = "rating",
    starIcon: ImageVector = Icons.Filled.Star,
    ratedStarsColor: Color = MaterialTheme.colorScheme.primary,
    unRatedStarsColor: Color = Color.LightGray,
    spaceBetween: Dp = (-2).dp
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = rating.value.toString(), style = MaterialTheme.typography.displaySmall)
        Row(
            horizontalArrangement = Arrangement.spacedBy(spaceBetween),
        ) {
            for (star in 1..numberOfStars) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = starIcon, contentDescription = contentDescription,
                    tint = if (star <= rating.starCount) ratedStarsColor else unRatedStarsColor
                )
            }
        }
    }
}

@Composable
fun RatingStar(starCount: Int) {
    Row(
        horizontalArrangement = Arrangement.spacedBy((-4).dp),
    ) {
        for (star in 1..starCount) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Outlined.Star, contentDescription = null,
                tint = Color.LightGray
            )
        }
    }
}

@Composable
fun RatingItem(starCount: Int, progress: Float) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.width(80.dp),
            horizontalArrangement = Arrangement.End
        ) {
            RatingStar(starCount)
        }
        LinearProgressIndicator(
            modifier = Modifier
                .weight(1f)
                .height(10.dp),
            progress = progress,
            trackColor = Color.White.copy(alpha = 0.2f)
        )
    }
}

@Composable
fun RatingBar(rating: Rating) {
    val percentageList = listOf(
        0.9f,
        0.09f,
        0.01f,
        0f,
        0f,
    )
    Column(modifier = Modifier.width(200.dp)) {
        percentageList.forEachIndexed { index, fl ->
            RatingItem(percentageList.size - index, fl)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Text(
                text = "${rating.count}人评分",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.alpha(0.5f)
            )
        }
    }
}

@Composable
fun RatingContent(rating: Rating) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        RatingScore(rating)
        RatingBar(rating)
    }
}

@Composable
fun DoubanRating() {
    val rating = movieDetail.rating
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Black.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "豆瓣评分®", style = MaterialTheme.typography.bodyMedium)
                Icon(Icons.Outlined.ChevronRight, contentDescription = null)
            }
            RatingContent(rating)
            Divider(
                modifier = Modifier
                    .padding(vertical = 8.dp),
                thickness = 0.5.dp,
                color = Color.White.copy(alpha = 0.5f)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "${rating.watched / 10000}万人看过",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.alpha(0.5f)
                )
                Text(
                    text = "${rating.unwatch / 10000}万人想看",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.alpha(0.5f)
                )
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 420)
@Composable
fun DetailScreenPreview() {
    DoubanMovieTheme(darkTheme = true) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DetailScreen()
        }
    }
}

private val movieDetail = MovieDetail(
    title = "肖申克的救赎",
    originalTitle = "The Shawshank Redemption",
    image = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp",
    intro = "一场谋杀案使银行家安迪（蒂姆•罗宾斯 Tim Robbins 饰）蒙冤入狱，谋杀妻子及其情人的指控将囚禁他终生。在肖申克监狱的首次现身就让监狱“大哥”瑞德（摩根•弗里曼 Morgan Freeman 饰）对他另眼相看。瑞德帮助他搞到一把石锤和一幅女明星海报，两人渐成患难 之交。很快，安迪在监狱里大显其才，担当监狱图书管理员，并利用自己的金融知识帮助监狱官避税，引起了典狱长的注意，被招致麾下帮助典狱长洗黑钱。偶然一次，他得知一名新入狱的小偷能够作证帮他洗脱谋杀罪。燃起一丝希望的安迪找到了典狱长，希望他能帮自己翻案。阴险伪善的狱长假装答应安迪，背后却派人杀死小偷，让他唯一能合法出狱的希望泯灭。沮丧的安迪并没有绝望，在一个电闪雷鸣的风雨夜，一场暗藏几十年的越狱计划让他自我救赎，重获自由！老朋友瑞德在他的鼓舞和帮助下，也勇敢地奔向自由。\\n本片获得1995年奥斯卡10项提名，以及金球奖、土星奖等多项提名。",
    year = "1994",
    genres = listOf("犯罪", "剧情"),
    countries = listOf("美国"),
    pubdate = listOf("1994-09-10(多伦多电影节)"),
    durations = "142分钟",
    honorInfos = listOf(
        HonorInfo(
            title = "豆瓣电影Top250",
            kind = "top250",
            rank = 1,
        )
    ),
    rating = Rating(
        count = 2729347,
        max = 10,
        starCount = 5,
        value = 9.7f,
        watched = 4394000,
        unwatch = 431000,
    ),
    vendorIcons = listOf(
        "https://img9.doubanio.com/f/frodo/88a62f5e0cf9981c910e60f4421c3e66aac2c9bc/pics/vendors/bilibili.png",
        "https://img2.doubanio.com/f/frodo/8286b9b5240f35c7e59e1b1768cd2ccf0467cde5/pics/vendors/migu_video.png",
        "https://img1.doubanio.com/f/frodo/9f302f2ad003c8c607cb79b447aca789a01142b2/pics/vendors/youku.png"
    ),
    subjectCollections = listOf(
        "评分最高剧情片",
        "美国犯罪片榜",
        "高分经典犯罪片榜",
    ),
    credits = listOf(
        Credit(
            category = "导演",
            name = "弗兰克·德拉邦特",
            latinName = "Frank Darabont",
            avatar = "https://img3.doubanio.com/view/celebrity/raw/public/p230.jpg",
            character = "导演 Director"
        ),
        Credit(
            category = "演员",
            name = "蒂姆·罗宾斯",
            latinName = "Tim Robbins",
            avatar = "https://img9.doubanio.com/view/celebrity/raw/public/p17525.jpg",
            character = "演员 Actor (饰 安迪·杜佛兰 Andy Dufresne)"
        ),
        Credit(
            category = "演员",
            name = "摩根·弗里曼",
            latinName = "Morgan Freeman",
            avatar = "https://img2.doubanio.com/view/celebrity/raw/public/p34642.jpg",
            character = "演员 Actor (饰 艾利斯·波伊德·“瑞德”·瑞丁 Ellis Boyd 'Red' Redding)"
        ),
        Credit(
            category = "演员",
            name = "鲍勃·冈顿",
            latinName = "Bob Gunton",
            avatar = "https://img1.doubanio.com/view/celebrity/raw/public/p5837.jpg",
            character = "演员 Actor (饰 监狱长山姆·诺顿 Warden Norton)",
        ),
        Credit(
            category = "演员",
            name = "威廉姆·赛德勒",
            latinName = "William Sadler",
            avatar = "https://img1.doubanio.com/view/celebrity/raw/public/p7827.jpg",
            character = "演员 Actor (饰 海伍德 Heywood)"
        ),
    ),
    trailer = Video(
        title = "预告片：25周年经典重映 (中文字幕)",
        videoUrl = "https://vt1.doubanio.com/202211041605/dcbc01ac836791534789a6f5b13b13f4/view/movie/M/402590258.mp4",
        coverUrl = "https://img1.doubanio.com/img/trailer/medium/2587159379.jpg?",
    ),
    videos = listOf(
        Video(
            title = "19分钟七大角度看懂《肖申克的救赎》",
            videoUrl = "https://sv1.doubanio.com/202211041604/f6af70fd712ac68f814c60c371ee85fb/video/2020/M/401060436.mp4",
            coverUrl = "https://img3.doubanio.com/view/photo/photo/public/p2616862940.jpg?",
        )
    ),
    photos = listOf(
        "https://img2.doubanio.com/view/photo/sqxs/public/p2561714803.webp",
        "https://img3.doubanio.com/view/photo/sqxs/public/p456482220.webp"
    )
)
