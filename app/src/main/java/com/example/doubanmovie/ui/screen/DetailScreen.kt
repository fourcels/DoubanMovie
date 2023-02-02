package com.example.doubanmovie.ui.screen

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.ContentAlpha
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.doubanmovie.R
import com.example.doubanmovie.data.*
import com.example.doubanmovie.ui.components.AsyncImage
import com.example.doubanmovie.ui.components.ExpandableText
import com.example.doubanmovie.ui.components.VideoView
import com.example.doubanmovie.ui.theme.DoubanMovieTheme
import com.webtoonscorp.android.readmore.material3.ReadMoreText
import com.example.doubanmovie.ui.components.RatingBar as RatingBarUI

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    onBackPressed: () -> Unit = {}
) {
    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(appBarState)
    val paddingTop = 300f
    val topbarContainerColor = remember(appBarState.contentOffset, paddingTop) {
        val alpha: Float
        val contentOffset = -appBarState.contentOffset
        alpha = if (contentOffset <= 0) {
            0f
        } else if (contentOffset > paddingTop) {
            1f
        } else {
            contentOffset / paddingTop
        }
        Color(0xFF4C271D).copy(alpha = alpha)
    }
    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .background(
                Brush.verticalGradient(
                    0.1f to Color(0xFF723B2C),
                    0.4f to Color(0xFF4C271D),
                )
            ),
        containerColor = Color.Transparent,
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Outlined.ArrowBackIosNew,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    Title(targetState = -appBarState.contentOffset < paddingTop)
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Outlined.MoreHoriz, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = topbarContainerColor,
                )
            )
        }

    ) { padding ->
        LazyColumn(contentPadding = padding) {
            item {
                Header()
            }
            item {
                DoubanRating()
            }
            item {
                MovieVenders()
            }
            item {
                Content()
            }
        }
    }
}

@Composable
fun Title(targetState: Boolean) {
    Crossfade(
        targetState = targetState
    ) { state ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp)
        ) {
            when (state) {
                true -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "电影",
                    )
                }
                false -> {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AsyncImage(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .width(30.dp)
                                .height(40.dp),
                            model = movieDetail.image
                        )
                        Column {
                            Text(
                                text = movieDetail.title,
                                style = MaterialTheme.typography.titleMedium
                            )
                            RatingBarUI(
                                rating = movieDetail.rating.value,
                                textStyle = MaterialTheme.typography.bodySmall,
                                textColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
                            )
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun HonorInfo(info: HonorInfo) {
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
            HonorInfo(item)
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
        horizontalArrangement = Arrangement.spacedBy((-2).dp),
    ) {
        for (star in 1..starCount) {
            Icon(
                modifier = Modifier.size(14.dp),
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
                .height(8.dp)
                .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)),
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


@Composable
fun MovieVenders() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_playable),
                    contentDescription = null,
                )
                Text(text = "播放源", style = MaterialTheme.typography.bodyLarge)
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    movieDetail.vendorIcons.forEach { item ->
                        AsyncImage(
                            model = item, modifier = Modifier
                                .width(24.dp)
                                .height(24.dp)
                        )
                    }
                }
                Icon(
                    Icons.Outlined.ChevronRight,
                    contentDescription = null,
                    modifier = Modifier.width(16.dp)
                )
            }
        }
        Divider(
            modifier = Modifier.padding(top = 16.dp),
            thickness = 0.5.dp,
            color = Color.White.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun Content() {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        SubjectList(list = movieDetail.subjectCollections)
        Introduction(intro = movieDetail.intro)
        CreditList(list = movieDetail.credits)
        Trailer(
            trailer = movieDetail.trailer,
            videos = movieDetail.videos,
            photos = movieDetail.photos
        )
        AwardList(movieDetail.awards)
    }
}

@Composable
fun ContentBox(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
    title: String,
    extra: String? = null,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            if (extra != null) {
                Row(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = extra,
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Icon(
                        imageVector = Icons.Outlined.ChevronRight,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        Box(modifier = Modifier.padding(contentPadding)) {
            content()
        }
    }
}

@Composable
fun AwardList(list: List<Award>) {
    ContentBox(
        title = "获奖记录",
        extra = "全部4"
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            list.forEach { item ->
                AwardItem(data = item)
            }
        }
    }
}

@Composable
fun AwardItem(data: Award) {
    Column {
        Text(text = data.title, style = MaterialTheme.typography.titleMedium)
        Text(
            text = listOf(data.name, data.winner).joinToString(separator = " "),
            modifier = Modifier.alpha(ContentAlpha.medium),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
fun Trailer(trailer: Video, videos: List<Video>, photos: List<String>) {
    ContentBox(
        title = "预告片 / 剧照",
        extra = "全部889",
        contentPadding = PaddingValues(0.dp),
    ) {
        LazyRow {
            item {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    VideoView(
                        modifier = Modifier
                            .width(250.dp)
                            .height(160.dp),
                        coverUrl = trailer.coverUrl
                    ) {
                        Surface(
                            modifier = Modifier
                                .align(Alignment.TopStart)
                                .padding(4.dp),
                            shape = RoundedCornerShape(2.dp),
                            color = MaterialTheme.colorScheme.primary,
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp),
                                text = "预告片",
                                color = Color.White,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    videos.forEach { item ->
                        VideoView(
                            modifier = Modifier
                                .width(250.dp)
                                .height(160.dp),
                            coverUrl = item.coverUrl
                        ) {
                            Surface(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(4.dp),
                                shape = RoundedCornerShape(2.dp),
                                color = Color.Black,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp),
                                    text = "视频评论",
                                    color = Color.White,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }

                    }
                    photos.forEach { item ->
                        AsyncImage(
                            modifier = Modifier
                                .width(250.dp)
                                .height(160.dp),
                            model = item
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun Introduction(intro: String) {
    val (expanded, onExpandedChange) = remember { mutableStateOf(false) }
    ContentBox(
        title = "简介",
    ) {
        ReadMoreText(
            text = intro,
            expanded = expanded,
            modifier = Modifier
                .clickable { onExpandedChange(!expanded) }
                .fillMaxWidth(),
            readMoreMaxLines = 3,
            readMoreText = "展开",
            readMoreColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium),
        )
    }
}

@Composable
fun CreditList(list: List<Credit>) {
    ContentBox(
        title = "演职员",
        extra = "全部44",
        contentPadding = PaddingValues(0.dp),
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(list) { item ->
                CreditItem(item = item)
            }
        }
    }
}

@Composable
fun CreditItem(item: Credit) {
    Column(modifier = Modifier.width(90.dp)) {
        AsyncImage(
            model = item.avatar,
            modifier = Modifier
                .width(90.dp)
                .height(120.dp)
                .clip(RoundedCornerShape(4.dp))
        )
        Text(
            text = item.name,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = item.character,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.5f)
        )
    }
}

@Composable
fun SubjectList(list: List<String>) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Spacer(modifier = Modifier.width(8.dp))
        }
        item {
            Text(
                text = "被收录于",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.5f)
            )
        }
        items(list) { item ->
            SubjectItem(name = item)
        }
        item {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun SubjectItem(name: String) {
    Card(
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.Black.copy(alpha = 0.2f)
        )
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier.size(16.dp),
                painter = painterResource(R.drawable.ic_subjects_ranking),
                contentDescription = null,
            )
            Text(text = name, style = MaterialTheme.typography.bodyMedium)
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null
            )
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
    ),
    awards = listOf(
        Award(
            title = "第67届奥斯卡金像奖",
            name = "最佳影片(提名)",
            winner = "妮基·马文"
        ),
        Award(
            title = "第52届金球奖",
            name = "电影类 剧情片最佳男主角(提名)",
            winner = "摩根·弗里曼"
        ),
        Award(
            title = "第19届日本电影学院奖",
            name = "最佳外语片",
            winner = "妮基·马文"
        ),
        Award(
            title = "第20届报知映画赏",
            name = "海外作品奖",
            winner = "弗兰克·德拉邦特"
        ),
    )
)
