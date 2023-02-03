package com.timothy.simplebookmarktask.domain.models

import androidx.compose.ui.graphics.Color

data class TaskItemModel(
    val id: Int,
    val title: String?,
    val duration: Int?,
    val themeColorString: String?,
    val themeColor: Color
)
