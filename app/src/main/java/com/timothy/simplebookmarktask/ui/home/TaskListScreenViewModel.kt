package com.timothy.simplebookmarktask.ui.home

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.timothy.simplebookmarktask.config.Response
import com.timothy.simplebookmarktask.data.db.TaskDatabase
import com.timothy.simplebookmarktask.data.db.entity.TaskItem
import com.timothy.simplebookmarktask.data.repository.TaskRepositoryImpl
import com.timothy.simplebookmarktask.domain.mapper.TaskItemMapper
import com.timothy.simplebookmarktask.domain.models.TaskItemModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class TaskListScreenViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val taskDatabase: TaskDatabase by lazy { TaskDatabase.invoke(application) }
    private val taskRepository by lazy { TaskRepositoryImpl(taskDatabase) }

    val taskList = mutableStateListOf<TaskItemModel>()

    init {
        fetchTaskFromDB()
    }

    private fun fetchTaskFromDB() = runBlocking<Unit> {
        taskRepository.getAllTask().onEach { result ->
            when (result) {
               is Response.Success -> {
                   result.data?.let { item ->
                       populateTask(item)
                   }
               }
                is Response.Error -> {
                    handleError()
                }
                is Response.Loading -> {
                    handleLoading()
                }
                else -> TODO()
            }
        }.launchIn(viewModelScope)
    }

    private fun handleError() {
        //TODO("Not yet implemented")
    }

    private fun handleLoading() {
        //TODO("Not yet implemented")
    }

    private fun populateTask(item: List<TaskItem>) {
        item.forEach{
            taskList.add( TaskItemMapper().mapDataModel(it))
        }
    }
}