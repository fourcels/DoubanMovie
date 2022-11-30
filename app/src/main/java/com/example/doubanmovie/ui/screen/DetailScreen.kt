package com.example.doubanmovie.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.doubanmovie.data.*
import com.example.doubanmovie.ui.theme.DoubanMovieTheme

@Composable
fun DetailScreen(
    onBackPressed: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.1f to Color(0xFF723B2C),
                    0.4f to Color(0xFF4C271D),
                )
            ),
        backgroundColor = Color.Transparent,
        topBar = {
            TopAppBar(
                modifier = Modifier.statusBarsPadding(),
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = null)
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
                backgroundColor = Color.Transparent,
                elevation = 0.dp
            )
        }

    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(text = "text")
        }
    }
}


@Preview(showBackground = true, widthDp = 420)
@Composable
fun DetailScreenPreview() {
    DoubanMovieTheme {
        DetailScreen()
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
