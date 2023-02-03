package com.timothy.simplebookmarktask.domain.models

import androidx.compose.ui.graphics.Color

data class TaskItemModel(
    val id: Int,
    val title: String?,
    val duration: String?,
    val themeColor: Color?
)
