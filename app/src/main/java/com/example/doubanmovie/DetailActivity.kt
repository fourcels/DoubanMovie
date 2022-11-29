package com.example.doubanmovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.doubanmovie.ui.screen.DetailScreen
import com.example.doubanmovie.ui.theme.DoubanMovieTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DoubanMovieTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(
                    color = primaryColor
                )
                systemUiController.setNavigationBarColor(
                    color = MaterialTheme.colors.background
                )
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DetailScreen(primaryColor = primaryColor, onBackPressed = {
                        this.finish()
                    })
                }
            }
        }
    }

}

private val primaryColor = Color(0xFF723b2c)