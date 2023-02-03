package com.timothy.simplebookmarktask.utilities

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.timothy.simplebookmarktask.utilities.GradientPositions.Companion.mapToOffset

class DesignUtils {
}

fun Modifier.gradientBackground(
    colors: List<Color>,
    startPosition: GradientPositions,
    endPosition: GradientPositions,
) = this.then(
    Modifier.drawBehind {
        drawRect(
            brush = Brush.linearGradient(
                colors = colors,
                start = startPosition.mapToOffset(size),
                end = endPosition.mapToOffset(size)
            ),
            size = size
        )
    }
)

enum class GradientPositions{
    TOP_START,
    TOP_END,
    BOTTOM_END,
    BOTTOM_START;


    companion object {
        fun GradientPositions.mapToOffset(size: Size): Offset {
            return when {
                this == TOP_START -> {
                    Offset(0f, 0f)
                }
                this == TOP_END -> {
                    Offset(size.width, 0f)
                }
                this == BOTTOM_END -> {
                    Offset(size.width, size.height)
                }
                else -> {
                    Offset(0f, size.height)
                }
            }
        }
    }
}