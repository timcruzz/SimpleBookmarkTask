package com.timothy.simplebookmarktask.domain.mapper

import android.graphics.Color
import com.timothy.simplebookmarktask.data.db.entity.TaskItem
import com.timothy.simplebookmarktask.domain.models.TaskItemModel
import com.timothy.simplebookmarktask.ui.theme.ColorTheme

class TaskItemMapper : DataMapper<TaskItemModel, TaskItem> {
    override fun mapDataModel(entity: TaskItem): TaskItemModel {
       return TaskItemModel(
            id = entity.id,
         title = entity.title,
         duration = entity.duration,
         themeColor = getColor(entity.themeColor.orEmpty())
       )
    }

    private fun getColor(strColor: String): androidx.compose.ui.graphics.Color {
        return when(strColor){
            "ORANGE" -> {
                ColorTheme.MainColor.orange
            }
            else -> {
                ColorTheme.MainColor.darkGreen
            }
        }
    }
}