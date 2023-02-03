package com.timothy.simplebookmarktask.data.db.entity

import androidx.compose.ui.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timothy.simplebookmarktask.data.db.entity.TaskItem.Companion.TASK_TABLE

@Entity(tableName = TASK_TABLE)
data class TaskItem(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "duration") var duration: String?,
    @ColumnInfo(name = "themeColor") var themeColor: String?
) {
    companion object {
        const val TASK_TABLE = "task_table"
    }
}