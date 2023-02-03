package com.timothy.simplebookmarktask.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.timothy.simplebookmarktask.data.dao.TaskDao
import com.timothy.simplebookmarktask.data.db.entity.TaskItem


@Database(
    entities = [TaskItem::class],
    version = 1,
    exportSchema = true
)

abstract class TaskDatabase : RoomDatabase(){

    abstract fun getTaskDao(): TaskDao

    companion object {
        const val DB_NAME = "simpletask_database.db"

        @Volatile
        private var INSTANCE: TaskDatabase? = null

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also{
                INSTANCE = it
            }
        }

        private fun buildDatabase(context: Context): TaskDatabase = try {
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                DB_NAME
            ).build()
        }catch (e: Exception){
            Room.databaseBuilder(
                context.applicationContext,
                TaskDatabase::class.java,
                DB_NAME
            ).build()
        }
    }
}