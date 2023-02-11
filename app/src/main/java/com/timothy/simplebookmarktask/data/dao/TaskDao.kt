package com.timothy.simplebookmarktask.data.dao

import androidx.room.*
import com.timothy.simplebookmarktask.data.db.entity.TaskItem

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskItem): Long

    @Update
    suspend fun updateTask(task:TaskItem)

    @Query("DELETE FROM ${TaskItem.TASK_TABLE} WHERE id = :id")
    suspend fun deleteTaskById(id: Int)

    @Query("SELECT * FROM ${TaskItem.TASK_TABLE} WHERE id = :id")
    suspend fun getTaskById(id: Int): TaskItem
    @Query("SELECT * FROM ${TaskItem.TASK_TABLE}")
    suspend fun getAllTask(): List<TaskItem>
}