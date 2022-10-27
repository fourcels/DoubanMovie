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