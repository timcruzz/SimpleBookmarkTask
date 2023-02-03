package com.timothy.simplebookmarktask.config

import com.timothy.simplebookmarktask.ui.theme.ColorTheme

enum class ColorPalette(val colorStr: String, val colorValue: androidx.compose.ui.graphics.Color) {
    ORANGE("ORANGE", ColorTheme.TileColor.orange),
    GREEN("GREEN", ColorTheme.TileColor.darkGreen),
    RED("RED", ColorTheme.TileColor.redPink),
    YELLOW("YELLOW", ColorTheme.TileColor.lightYellow),
    BLUE("BLUE", ColorTheme.TileColor.darkBlue),
    VIOLET("VIOLET", ColorTheme.TileColor.lightViolet)
}