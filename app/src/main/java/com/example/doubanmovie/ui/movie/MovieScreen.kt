package com.example.doubanmovie.ui.movie

import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider
import androidx.compose.ui.window.PopupProperties
import com.example.doubanmovie.R
import com.example.doubanmovie.data.MovieComment
import com.example.doubanmovie.data.MovieItem
import com.example.doubanmovie.ui.components.AsyncImage
import com.example.doubanmovie.ui.components.RatingBar
import com.example.doubanmovie.ui.theme.DoubanMovieTheme
import com.example.doubanmovie.ui.theme.LightGrey
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieScreen() {
    val listState = rememberLazyListState()
    // Remember a CoroutineScope to be able to launch
    val coroutineScope = rememberCoroutineScope()
    LazyColumn(state = listState) {
        stickyHeader {
            TabBar()
        }
        item {
            NavList(onSearch = {
                coroutineScope.launch {
                    // Animate scroll to the 10th item
                    listState.animateScrollToItem(index = 6)
                }
            })
        }
        item {

            HitTheaters()
        }
        item {

            ComingMovie()
        }
        item {

            MovieRank()
        }
        item {
            SearchMovieHead()
        }
        stickyHeader {
            SearchMovieBody(onFilterItemClick = {
                coroutineScope.launch {
                    // Animate scroll to the 10th item
                    listState.scrollToItem(index = 6)
                }
            })
        }
        item {
            SearchMovieList()
        }
    }
}

@Composable
fun NavList(onSearch: () -> Unit = {}) {
    val navList = listOf(
        NavItem("找电影", R.drawable.ic_subjects_categories_movie),
        NavItem("豆瓣榜单", R.drawable.ic_subjects_ranking),
        NavItem("即将上映", R.drawable.ic_subjects_time),
        NavItem("豆瓣片单", R.drawable.ic_subjects_movie_lists),
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        navList.forEachIndexed { index, navItem ->
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.background
                ),
                shape = RoundedCornerShape(20),
                onClick = if (index == 0) onSearch else ({})
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painter = painterResource(navItem.icon), null)
                    Text(
                        text = navItem.name,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.alpha(ContentAlpha.medium)
                    )
                }
            }
        }
    }
}

@Composable
fun HitTheaters() {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        HitHeader()
        HitBody()
    }
}

@Composable
fun HitHeader() {
    Row(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HitTabbar()
        Spacer(modifier = Modifier.weight(1f))
        ShowMore()
    }
}

@Composable
fun HitBody() {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        item {}
        items(movieList) { item ->
            Column {
                Box(modifier = Modifier.clip(RoundedCornerShape(8.dp))) {
                    AsyncImage(
                        model = item.image,
                        modifier = Modifier
                            .width(110.dp)
                            .height(160.dp),
                    )
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .clip(RoundedCornerShape(bottomEnd = 8.dp)),
                        color = Color.Black.copy(alpha = 0.3f),
                        contentColor = Color.White,
                    ) {
                        Icon(
                            modifier = Modifier.padding(2.dp),
                            imageVector = if (item.favorite) Icons.Outlined.Done else Icons.Outlined.FavoriteBorder,
                            contentDescription = null
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.body2,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                RatingBar(rating = item.rating)

            }
        }
        item {}
    }
}


@Composable
fun ShowMore() {
    val total = 33
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(text = "全部$total", style = MaterialTheme.typography.body2)
        Icon(
            imageVector = Icons.Filled.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun HitTabbar() {
    val hitList = listOf("影院热映", "豆瓣热门")
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
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }
}

@Composable
fun ComingMovie() {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(16.dp)
    ) {
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
                            modifier = Modifier
                                .width(55.dp)
                                .height(50.dp)
                                .clip(RoundedCornerShape(10.dp)),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MovieRank() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .padding(vertical = 16.dp)
            .height(240.dp)
    ) {
        item {}
        items(movieRankList) { item ->
            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(280.dp),
                shape = RoundedCornerShape(10.dp),
                contentColor = Color.White,
            ) {
                AsyncImage(
                    model = item.image,
                    modifier = Modifier
                        .fillMaxSize(),
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                0.1f to Color(0x00FFFFFF),
                                0.4f to item.color,
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(PaddingValues(16.dp)),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(text = item.title, style = MaterialTheme.typography.h6)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "豆瓣榜单", style = MaterialTheme.typography.body2)
                        }
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            item.items.forEachIndexed { index, movieItem ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Text(text = (index + 1).toString())
                                    AsyncImage(
                                        model = movieItem.image,
                                        modifier = Modifier
                                            .width(30.dp)
                                            .height(42.dp)
                                            .clip(RoundedCornerShape(4.dp)),
                                    )
                                    Column {
                                        Text(
                                            text = movieItem.name,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        RatingBar(rating = movieItem.rating)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        item {
            Surface(
                modifier = Modifier.fillMaxHeight(),
                color = LightGrey,
                shape = MaterialTheme.shapes.medium,
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        modifier = Modifier.width(14.dp),
                        text = "更多",
                        style = MaterialTheme.typography.caption.copy(
                            lineHeight = 14.sp,
                        ),
                    )
                    Icon(
                        imageVector = Icons.Outlined.KeyboardArrowRight,
                        contentDescription = "more",
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
        item {}
    }
}

@Composable
fun SearchMovieHead() {
    Surface(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
        Text(
            text = "找电影",
            style = MaterialTheme.typography.h5,
        )
    }
}

@Composable
fun SearchMovieBody(onFilterItemClick: () -> Unit = {}) {
    var current by remember {
        mutableStateOf(-1)
    }
    val tagList = listOf(
        "公路", "时空", "宝莱坞", "童话",
        "浪漫", "哥特", "梦想", "足球",
        "怪物", "唯美", "民国", "神话",
    )
    Surface {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CanPlay()
                filterList.forEachIndexed { index, filterItem ->
                    FilterItem(
                        data = filterItem,
                        selected = current == index,
                        onClick = {
                            current = if (current == index) {
                                -1
                            } else {
                                onFilterItemClick()
                                index
                            }
                        },
                        onDismissRequest = {
                            current = -1
                        },
                    )
                }
                FilterMore()
            }
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 16.dp),
            ) {
                item {
                    Spacer(modifier = Modifier.width(8.dp))
                }
                items(tagList) { item ->
                    Surface(
                        color = Color.LightGray.copy(0.2f),
                        shape = MaterialTheme.shapes.small,
                    ) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Composable
fun CanPlay() {
    var selected by remember {
        mutableStateOf(false)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.selectable(
            selected = selected,
            onClick = {
                selected = !selected
            }
        )
    ) {
        Icon(
            imageVector = Icons.Outlined.PlayCircleOutline, contentDescription = "can play",
            modifier = Modifier.size(16.dp),
            tint = if (selected) MaterialTheme.colors.primary else Color.Red,
        )
        Text(
            text = "可播放",
            style = MaterialTheme.typography.caption,
            color = if (selected) MaterialTheme.colors.primary else LocalContentColor.current
        )
    }
}

@Composable
fun FilterItem(
    selected: Boolean = false,
    data: FilterData,
    onClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {

    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.selectable(
                selected = selected,
                onClick = onClick,
            )
        ) {
            Text(text = data.name, style = MaterialTheme.typography.caption)
            Icon(
                imageVector = if (selected) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                contentDescription = null,
                Modifier.size(16.dp)
            )
        }
        Box {
            Popup(
                popupPositionProvider = CustomPopupPositionProvider(),
                properties = PopupProperties(clippingEnabled = false)
            ) {
                if (selected) {
                    Surface(
                        color = Color.Black.copy(alpha = 0.1f),
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(onClick = onDismissRequest)
                    ) {}
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ) {
                    if (selected) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            userScrollEnabled = false,
                            contentPadding = PaddingValues(12.dp)
                        ) {
                            items(data.items) { item ->
                                Surface(
                                    modifier = Modifier.padding(4.dp),
                                    color = LightGrey,
                                    shape = MaterialTheme.shapes.small
                                ) {
                                    Text(
                                        item,
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.body2,
                                        modifier = Modifier.padding(vertical = 8.dp)
                                    )
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

class CustomPopupPositionProvider(
    private val x: Int = 0,
    private val y: Int = 0
) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset = IntOffset(
        0 + x,
        anchorBounds.top + y
    )
}


@Composable
fun FilterMore() {
    Icon(
        Icons.Outlined.FilterAlt,
        contentDescription = null,
        Modifier.size(16.dp),
    )
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
                modifier = Modifier.height(48.dp),
                selected = currentIndex == index,
                onClick = { currentIndex = index }) {
                Text(text = item)
            }
        }
    }
}


@Composable
fun SearchMovieList() {
    Column {
        searchMovieList.forEach { item ->
            SearchMovieItem(item = item)
        }
    }
}

@Composable
fun SearchMovieItem(item: MovieItem) {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .height(150.dp)
        ) {
            AsyncImage(
                model = item.image,
                modifier = Modifier
                    .width(110.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp)),
            )
            Spacer(modifier = Modifier.width(16.dp))
            PhotoPager(item.photos)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .width(0.dp)
                    .weight(1f)
            ) {
                Text(
                    text = "${item.name} (${item.year})",
                    style = MaterialTheme.typography.subtitle1
                )
                Spacer(modifier = Modifier.height(4.dp))
                RatingBar(rating = item.rating)
                Text(
                    text = item.subtitle,
                    style = MaterialTheme.typography.caption,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    maxLines = 1,
                )
            }
            MovieFavorite(favorite = item.favorite)
        }
        Column {
            MovieComment(item.comment)
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            MovieTags(tags = item.tags)
        }
    }
}

@Composable
fun MovieFavorite(favorite: Boolean = false) {
    Surface(
        color = Color.Transparent,
        contentColor = if (favorite) Color.Gray else MaterialTheme.colors.secondary
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(40.dp)
        ) {
            Icon(
                imageVector = if (favorite) Icons.Outlined.Done else Icons.Outlined.FavoriteBorder,
                contentDescription = null
            )
            Text(
                text = if (favorite) "已想看" else "想看",
                style = MaterialTheme.typography.caption,
            )
        }
    }
}

@Composable
fun MovieTags(tags: List<String>) {
    FlowRow(mainAxisSpacing = 8.dp, crossAxisSpacing = 8.dp) {
        tags.forEach { item ->
            Surface(color = LightGrey, shape = MaterialTheme.shapes.small) {
                Row(
                    modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = item, style = MaterialTheme.typography.caption)
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Outlined.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Composable
fun MovieComment(comment: MovieComment) {
    val text = buildAnnotatedString {
        append(AnnotatedString(comment.content))
        append(AnnotatedString(" — ${comment.user}", spanStyle = SpanStyle(Color.Gray)))
    }
    Text(text = text, style = MaterialTheme.typography.body2)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PhotoPager(photos: List<String>) {
    val pagerState = rememberPagerState()
    Box {
        HorizontalPager(
            count = photos.size,
            state = pagerState,
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
        ) { page ->
            AsyncImage(
                model = photos[page],
                modifier = Modifier
                    .fillMaxSize(),
            )
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colors.background,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchMovieListPreview() {
    DoubanMovieTheme {
        SearchMovieList()
    }
}

@Preview(showBackground = true)
@Composable
fun SearchMovieBodyPreview() {
    DoubanMovieTheme {
        SearchMovieBody()
    }
}

@Preview(showBackground = true)
@Composable
fun TabBarPreview() {
    DoubanMovieTheme {
        TabBar()
    }
}

//@Preview(showBackground = true, widthDp = 420)
//@Composable
//fun SearchMoviePreview() {
//    DoubanMovieTheme {
//        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
//            SearchMovie()
//        }
//    }
//}

@Preview(showBackground = true, widthDp = 420)
@Composable
fun MovieRankPreview() {
    DoubanMovieTheme {
        MovieRank()
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
        NavList()
    }
}

@Preview(showBackground = true, widthDp = 420)
@Composable
fun MovieScreenPreview() {
    DoubanMovieTheme {
        MovieScreen()
    }
}

data class FilterData(val name: String, val items: List<String>)

private val filterList = listOf(
    FilterData(
        "类型",
        listOf(
            "全部类型", "喜剧", "爱情", "动作",
            "科幻", "动画", "悬疑", "犯罪",
            "惊悚", "冒险", "音乐", "历史",
            "奇幻", "恐怖", "战争", "传记",
            "歌舞", "武侠", "情色", "灾难",
            "西部", "纪录片", "短片"
        )
    ),
    FilterData(
        "地区",
        listOf(
            "全部地区", "华语", "欧美", "韩国",
            "日本", "中国大陆", "美国", "中国香港",
            "中国台湾", "英国", "法国", "德国",
            "意大利", "西班牙", "印度", "泰国",
            "俄罗斯", "加拿大", "澳大利亚", "爱尔兰",
            "瑞典", "巴西", "丹麦"
        )
    ),
    FilterData(
        "排序",
        listOf(
            "综合排序", "近期热度", "首映时间", "高分优先",
        )
    ),
)

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

data class MovieRankItem(
    val title: String,
    val image: String,
    val color: Color,
    val items: List<MovieItem>
)

private val movieRankList = listOf(
    MovieRankItem(
        title = "实时热门电影",
        image = "https://img1.doubanio.com/view/photo/photo/public/p2560609439.webp",
        color = Color(0xFFA58A76),
        items = listOf(
            MovieItem(
                name = "全金属外壳",
                image = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2553116438.webp",
                rating = 8.6f,
            ),
            MovieItem(
                name = "万里归途",
                image = "https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2881176356.webp",
                rating = 7.4f,
            ),
            MovieItem(
                name = "新·奥特曼",
                image = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2871244838.webp",
                rating = 7.2f,
            ),
        )
    ),
    MovieRankItem(
        title = "一周口碑电影榜",
        image = "https://img9.doubanio.com/view/photo/photo/public/p2799949526.webp",
        color = Color(0xFF6F6672),
        items = listOf(
            MovieItem(
                name = "咒术回战 0",
                image = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2708190250.webp",
                rating = 8.2f,
            ),
            MovieItem(
                name = "子弹列车",
                image = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p2873950053.webp",
                rating = 7.9f,
            ),
            MovieItem(
                name = "一场很（没）有必要的春晚",
                image = "https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2868008846.webp",
                rating = 7.7f,
            ),
        )
    ),
    MovieRankItem(
        title = "豆瓣电影 Top250",
        image = "https://img3.doubanio.com/view/photo/photo/public/p456482220.webp",
        color = Color(0xFF32323B),
        items = listOf(
            MovieItem(
                name = "肖申克的救赎",
                image = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp",
                rating = 9.7f,
            ),
            MovieItem(
                name = "霸王别姬",
                image = "https://img3.doubanio.com/view/photo/s_ratio_poster/public/p2561716440.webp",
                rating = 9.6f,
            ),
            MovieItem(
                name = "阿甘正传",
                image = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p2372307693.webp",
                rating = 9.5f,
            ),
        )
    ),
)


data class NavItem(
    val name: String,
    @DrawableRes val icon: Int,
)

private val movieList = listOf(
    MovieItem(
        "万里归途",
        "https://img9.doubanio.com/view/photo/m_ratio_poster/public/p2881176356.webp",
        rating = 7.4f,
        favorite = true,
    ),
    MovieItem(
        "还是觉得你最好",
        "https://img2.doubanio.com/view/photo/m_ratio_poster/public/p2879301401.webp",
        rating = 7.6f
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

private val searchMovieList = listOf(
    MovieItem(
        name = "肖申克的救赎",
        image = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp",
        rating = 9.7f,
        favorite = false,
        year = "1994",
        comment = MovieComment(
            content = "恐惧让你沦为囚犯，希望让你重获自由。——《肖申克的救赎》",
            user = "如小果"
        ),
        subtitle = "1994 / 美国 / 犯罪 剧情 / 弗兰克·德拉邦特 / 蒂姆·罗宾斯 摩根·弗里曼",
        tags = listOf("美国 自由 人生", "奥斯卡金像奖 最佳摄影 获奖作品"),
        photos = listOf(
            "https://img3.doubanio.com/view/photo/m/public/p456482220.webp",
            "https://img3.doubanio.com/view/photo/m/public/p490576110.webp",
            "https://img2.doubanio.com/view/photo/m/public/p825401501.webp",
            "https://img1.doubanio.com/view/photo/m/public/p490577287.webp"
        )
    ),
    MovieItem(
        name = "千与千寻",
        image = "https://img1.doubanio.com/view/photo/s_ratio_poster/public/p2557573348.webp",
        rating = 9.4f,
        favorite = true,
        year = "2001",
        comment = MovieComment(
            content = "每次看这些神作的时候想到这是动画片这是一群牛人一笔一笔画出来的,就觉得漏看掉一帧都实在是对不起他们啊",
            user = "zing"
        ),
        subtitle = "2001 / 日本 / 剧情 动画 奇幻 / 宫崎骏 / 柊瑠美 入野自由",
        tags = listOf("日本 动画 温情", "英国电影学院奖 最佳非英语片 获奖作品"),
        photos = listOf(
            "https://img9.doubanio.com/view/photo/m/public/p2512318945.webp",
            "https://img9.doubanio.com/view/photo/m/public/p2563777035.webp",
            "https://img1.doubanio.com/view/photo/m/public/p2181503417.webp",
            "https://img1.doubanio.com/view/photo/m/public/p2512318939.webp"
        )
    ),
    MovieItem(
        name = "阿甘正传",
        image = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p2372307693.webp",
        rating = 9.5f,
        favorite = false,
        year = "1994",
        comment = MovieComment(
            content = "羡慕珍妮，不管她多么叛逆、落魄、堕落，永远有阿甘在等她回来",
            user = "今天小熊不吃糖"
        ),
        subtitle = "1994 / 美国 / 剧情 爱情 / 罗伯特·泽米吉斯 / 汤姆·汉克斯 罗宾·怀特",
        tags = listOf("美国 人生 温情", "奥斯卡金像奖 最佳音响 获奖作品"),
        photos = listOf(
            "https://img2.doubanio.com/view/photo/m/public/p1484731332.webp",
            "https://img9.doubanio.com/view/photo/m/public/p1484731424.webp",
            "https://img2.doubanio.com/view/photo/m/public/p825974951.webp",
            "https://img1.doubanio.com/view/photo/m/public/p825966139.webp"
        )
    ),
    MovieItem(
        name = "楚门的世界",
        image = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p479682972.webp",
        rating = 9.3f,
        favorite = false,
        year = "1998",
        comment = MovieComment(
            content = "如果再也不能见到你，祝你早安，午安和晚安。",
            user = "影志"
        ),
        subtitle = "1998 / 美国 / 剧情 科幻 / 彼得·威尔 / 金·凯瑞 劳拉·琳妮",
        tags = listOf("美国 人生 自由", "金球奖 最佳导演 获奖作品"),
        photos = listOf(
            "https://img9.doubanio.com/view/photo/m/public/p764864325.webp",
            "https://img2.doubanio.com/view/photo/m/public/p2614864522.webp",
            "https://img1.doubanio.com/view/photo/m/public/p2614740719.webp",
            "https://img2.doubanio.com/view/photo/m/public/p2614740691.webp"
        )
    ),
    MovieItem(
        name = "三傻大闹宝莱坞",
        image = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p579729551.webp",
        rating = 9.2f,
        favorite = true,
        year = "2009",
        comment = MovieComment(
            content = "看了这部电影是我2010年最幸运的一件事 力荐！",
            user = "私喜"
        ),
        subtitle = "2009 / 印度 / 剧情 喜剧 爱情 / 拉吉库马尔·希拉尼 / 阿米尔·汗 卡琳娜·卡普尔",
        tags = listOf("印度 喜剧 搞笑", "日本电影学院奖 最佳外语片 获奖作品"),
        photos = listOf(
            "https://img2.doubanio.com/view/photo/m/public/p2571368413.webp",
            "https://img2.doubanio.com/view/photo/m/public/p1303106712.webp",
            "https://img1.doubanio.com/view/photo/m/public/p579722647.webp",
            "https://img9.doubanio.com/view/photo/m/public/p1303107905.webp"
        )
    ),
    MovieItem(
        name = "海上钢琴师",
        image = "https://img9.doubanio.com/view/photo/s_ratio_poster/public/p2574551676.webp",
        rating = 9.3f,
        favorite = false,
        year = "1998",
        comment = MovieComment(
            content = "我总觉得这是一个真实的故事，因为他的懦弱，懦弱得真实。",
            user = "Zuschauerin"
        ),
        subtitle = "1998 / 意大利 / 剧情 音乐 / 朱塞佩·托纳多雷 / 蒂姆·罗斯 普路特·泰勒·文斯",
        tags = listOf("意大利 人生 音乐", "金球奖 最佳原创配乐 获奖作品"),
        photos = listOf(
            "https://img2.doubanio.com/view/photo/m/public/p2581352902.webp",
            "https://img1.doubanio.com/view/photo/m/public/p2574724747.webp",
            "https://img3.doubanio.com/view/photo/m/public/p2573277220.webp",
            "https://img2.doubanio.com/view/photo/m/public/p826011731.webp"
        )
    ),
    MovieItem(
        name = "阿凡达",
        image = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p2634997853.webp",
        rating = 8.8f,
        favorite = false,
        year = "2009",
        comment = MovieComment(
            content = "钉子户 大战 城管 in 3D",
            user = "Menphis"
        ),
        subtitle = "2009 / 美国 / 动作 科幻 冒险 / 詹姆斯·卡梅隆 / 萨姆·沃辛顿 佐伊·索尔达娜",
        tags = listOf("美国 科幻 史诗", "奥斯卡金像奖 最佳原创配乐 获奖作品"),
        photos = listOf(
            "https://img9.doubanio.com/view/photo/m/public/p595308795.webp",
            "https://img9.doubanio.com/view/photo/m/public/p455284965.webp",
            "https://img2.doubanio.com/view/photo/m/public/p595295073.webp",
            "https://img9.doubanio.com/view/photo/m/public/p595258686.webp"
        )
    ),
    MovieItem(
        name = "白日梦想家",
        image = "https://img2.doubanio.com/view/photo/s_ratio_poster/public/p2160195181.webp",
        rating = 8.5f,
        favorite = false,
        year = "2013",
        comment = MovieComment(
            content = "在片中《LIFE》杂志闭刊号封面出现的那一刻，我完全被击穿了。",
            user = "仙剑风晨"
        ),
        subtitle = "2013 / 美国 英国 / 剧情 喜剧 冒险 / 本·斯蒂勒 / 本·斯蒂勒 克里斯汀·韦格",
        tags = listOf("美国 冒险 人生"),
        photos = listOf(
            "https://img1.doubanio.com/view/photo/m/public/p2176074878.webp",
            "https://img9.doubanio.com/view/photo/m/public/p2164194895.webp",
            "https://img1.doubanio.com/view/photo/m/public/p2164057058.webp",
            "https://img1.doubanio.com/view/photo/m/public/p2159273167.webp"
        )
    ),
)
