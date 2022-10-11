package com.example.doubanmovie.data

data class MovieItem(val name: String, val image: String, val rating: Float?, var favorite: Boolean = false)