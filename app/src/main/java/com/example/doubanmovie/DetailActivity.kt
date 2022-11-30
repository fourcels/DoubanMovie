package com.example.doubanmovie

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.doubanmovie.ui.screen.DetailScreen
import com.example.doubanmovie.ui.theme.DoubanMovieTheme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //or
        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContent {
            DoubanMovieTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    DetailScreen(onBackPressed = {
                        this.finish()
                    })
                }
                // A surface container using the 'background' color from the theme
            }
        }
    }

}