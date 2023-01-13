package com.example.doubanmovie.data

data class MovieComment(val content: String = "", val user: String = "")

data class MovieItem(
    val name: String,
    val image: String,
    val rating: Float? = null,
    val favorite: Boolean = false,
    val year: String = "",
    val comment: MovieComment = MovieComment(),
    val subtitle: String = "",
    val photos: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
)


data class HonorInfo(
    val kind: String,
    val rank: Int,
    val title: String,
)

data class Rating(
    val count: Int,
    val max: Int,
    val starCount: Int,
    val value: Float?,
    val watched: Int,
    val unwatch: Int,
)

data class Credit(
    val category: String,
    val name: String,
    val latinName: String,
    val character: String,
    val avatar: String,
)

data class Video(
    val title: String,
    val videoUrl: String,
    val coverUrl: String,
)

data class Award(
    val title: String,
    val name: String,
    val winner: String,
)

data class MovieDetail(
    val title: String,
    val originalTitle: String,
    val image: String,
    val intro: String,
    val year: String,
    val genres: List<String> = emptyList(),
    val countries: List<String> = emptyList(),
    val pubdate: List<String> = emptyList(),
    val durations: String,
    val honorInfos: List<HonorInfo> = emptyList(),
    val rating: Rating,
    val vendorIcons: List<String> = emptyList(),
    val subjectCollections: List<String> = emptyList(),
    val credits: List<Credit> = emptyList(),
    val trailer: Video,
    val videos: List<Video> = emptyList(),
    val photos: List<String> = emptyList(),
    val awards: List<Award> = emptyList(),
)