package com.timothy.simplebookmarktask.domain.mapper

import com.timothy.simplebookmarktask.config.ColorPalette
import com.timothy.simplebookmarktask.data.db.entity.TaskItem
import com.timothy.simplebookmarktask.domain.models.TaskItemModel

class TaskItemMapper : DataMapper<TaskItemModel, TaskItem> {
    override fun mapDataModel(dataModel: TaskItem): TaskItemModel {
        return TaskItemModel(
            id = dataModel.id,
            title = dataModel.title,
            duration = dataModel.duration,
            themeColor = getActualColor(dataModel.themeColor.orEmpty()),
            themeColorString = dataModel.themeColor.orEmpty()
        )
    }
}

fun getActualColor(strColor: String): androidx.compose.ui.graphics.Color {
    return when (strColor) {
        ColorPalette.GREEN.colorStr -> {
            ColorPalette.GREEN.colorValue
        }
        ColorPalette.ORANGE.colorStr -> {
            ColorPalette.ORANGE.colorValue
        }
        ColorPalette.RED.colorStr -> {
            ColorPalette.RED.colorValue
        }
        ColorPalette.YELLOW.colorStr -> {
            ColorPalette.YELLOW.colorValue
        }
        ColorPalette.BLUE.colorStr -> {
            ColorPalette.BLUE.colorValue
        }
        ColorPalette.VIOLET.colorStr -> {
            ColorPalette.VIOLET.colorValue
        }
        else -> {
            ColorPalette.RED.colorValue
        }
    }
}

fun getColorString(color: androidx.compose.ui.graphics.Color?): String {
    return when (color) {
        ColorPalette.GREEN.colorValue -> {
            ColorPalette.GREEN.colorStr
        }
        ColorPalette.ORANGE.colorValue -> {
            ColorPalette.ORANGE.colorStr
        }
        ColorPalette.RED.colorValue -> {
            ColorPalette.RED.colorStr
        }
        ColorPalette.YELLOW.colorValue -> {
            ColorPalette.RED.colorStr
        }
        ColorPalette.BLUE.colorValue -> {
            ColorPalette.RED.colorStr
        }
        ColorPalette.VIOLET.colorValue -> {
            ColorPalette.RED.colorStr
        }
        else -> {
            ColorPalette.RED.colorStr
        }
    }
}

class TaskItemModelMapper : DataMapper<TaskItem, TaskItemModel> {
    override fun mapDataModel(dataModel: TaskItemModel): TaskItem {
        return TaskItem(
            id = dataModel.id,
            title = dataModel.title,
            duration = dataModel.duration,
            themeColor = dataModel.themeColorString
        )
    }
}