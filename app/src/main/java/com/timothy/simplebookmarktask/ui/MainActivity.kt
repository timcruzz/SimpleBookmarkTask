package com.timothy.simplebookmarktask.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.timothy.simplebookmarktask.ui.navigation.NavigationHandler
import com.timothy.simplebookmarktask.ui.theme.ColorTheme
import com.timothy.simplebookmarktask.ui.theme.SimpleBookmarkTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleBookmarkTaskTheme {
                Box {
                    val navController = rememberNavController()
                    NavigationHandler(navController = navController)
                }
            }
        }
    }
}
