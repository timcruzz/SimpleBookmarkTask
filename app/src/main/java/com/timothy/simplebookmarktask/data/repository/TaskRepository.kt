package com.timothy.simplebookmarktask.data.repository

import com.timothy.simplebookmarktask.config.Response
import com.timothy.simplebookmarktask.data.db.TaskDatabase
import com.timothy.simplebookmarktask.data.db.entity.TaskItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


interface TaskRepository {
    suspend fun getAllTask(): Flow<Response<List<TaskItem>>?>
    suspend fun getTaskById(id: Int):  Flow<Response<TaskItem>>
    suspend fun insertTask(taskItem: TaskItem): Flow<Response<TaskItem>>
    suspend fun updateTask(taskItem: TaskItem): Flow<Response<TaskItem>>
    suspend fun deleteTaskById(id: Int): Flow<Response<Boolean>>
}

class TaskRepositoryImpl(
    private val taskDatabase: TaskDatabase
) : TaskRepository {

    override suspend fun getAllTask(): Flow<Response<List<TaskItem>>?> {
       return flow {
           emit(Response.Loading())
           val allTask = taskDatabase.getTaskDao().getAllTask()
           emit(Response.Success(allTask))
       }.flowOn(Dispatchers.IO)
    }

    override suspend fun getTaskById(id: Int): Flow<Response<TaskItem>> {
        return flow {
            emit(Response.Loading())
            val allTask = taskDatabase.getTaskDao().getTaskById(id)
            emit(Response.Success(allTask))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun insertTask(taskItem: TaskItem): Flow<Response<TaskItem>> {
        return flow {
            emit(Response.Loading())
            taskDatabase.getTaskDao().insertTask(taskItem)
            emit(Response.Success(taskItem))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun updateTask(taskItem: TaskItem): Flow<Response<TaskItem>> {
        return flow {
            emit(Response.Loading())
            taskDatabase.getTaskDao().updateTask(taskItem)
            emit(Response.Success(taskItem))
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun deleteTaskById(id: Int): Flow<Response<Boolean>> {
        return flow {
            emit(Response.Loading())
            taskDatabase.getTaskDao().deleteTaskById(id)
            emit(Response.Success(true))
        }.flowOn(Dispatchers.IO)
    }
}