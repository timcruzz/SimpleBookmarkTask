package com.timothy.simplebookmarktask.ui.theme

import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

sealed class ColorTheme {
    sealed class MainColor{
        companion object {
            val lightBrown = Color(0xFFEEE3CB)
            val midBrown = Color(0xFFD7C0AE)
            val darkBrown = Color(0xFF967E76)
            val blackBrown = Color(0xFF594545)
            val skyBlue = Color(0xFFB7C4CF)
            val skyBluer = Color(0xFF6096B4)
            val lightRed = Color(0xFFA7727D)
            val black = Color(0xFF1A120B)
            val creamWhite = Color(0xFFFAF8F1)
            val orange = Color(0xFFFF6E31)
            val darkBlue = Color(0xFF243763)
            val darkGreen = Color(0xFF395144)
            val gray = Color(0xFF656869)
        }
    }

    sealed class TileColor {
        companion object {
            val black = Color(0xFF413F3D)
            val alpha = Color(0x39FFFFFF)
            val orange = Color(0xFFFF6E31)
            val darkGreen = Color(0xFF395144)
            val darkBlue = Color(0xFF243763)
            val redPink = Color(0xFFE91E63)
            val lightViolet = Color(0xFF9C27B0)
            val lightYellow = Color(0xFFFFEB3B)
            val skyBlue = Color(0xFF03A9F4)
            val blackBrown = Color(0xFF594545)
            val creamWhite = Color(0xFFF0EDE4)
        }
    }
}