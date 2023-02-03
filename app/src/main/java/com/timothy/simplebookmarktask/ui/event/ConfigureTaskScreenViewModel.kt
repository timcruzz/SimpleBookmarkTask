package com.timothy.simplebookmarktask.ui.event

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.timothy.simplebookmarktask.config.Constants
import com.timothy.simplebookmarktask.config.Response
import com.timothy.simplebookmarktask.data.db.TaskDatabase
import com.timothy.simplebookmarktask.data.db.entity.TaskItem
import com.timothy.simplebookmarktask.data.repository.TaskRepositoryImpl
import com.timothy.simplebookmarktask.domain.mapper.TaskItemModelMapper
import com.timothy.simplebookmarktask.domain.models.TaskItemModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class ConfigureTaskScreenViewModel(
    application: Application,
    private val navController: NavHostController,
) : AndroidViewModel(application) {

    private val taskDatabase: TaskDatabase by lazy { TaskDatabase.invoke(application) }
    private val taskRepository by lazy { TaskRepositoryImpl(taskDatabase) }

    val taskItem: MutableState<TaskItemModel?> = mutableStateOf(null)

    val configIdentifier: MutableState<String?> =
        mutableStateOf(Constants.ConfigIdentifier.ADD_TASK_KEY)

    init {
        loadFromBackStack()
    }

    private fun loadFromBackStack() {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<TaskItemModel>(Constants.ConfigIdentifier.UPDATE_TASK_KEY)?.let {
                taskItem.value = it
            }
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<String>(Constants.ConfigIdentifier.IDENTIFIER_KEY)?.let {
                configIdentifier.value = it
            }
    }

    fun updateOrAddTaskDB(taskItem: TaskItemModel) {
        if (configIdentifier.value == Constants.ConfigIdentifier.UPDATE_TASK_KEY) {
            updateTaskFromDB(taskItem)
        } else {
            addTaskFromDB(taskItem)
        }
    }

    private fun updateTaskFromDB(value: TaskItemModel?) = runBlocking<Unit> {
        value?.let {
            taskRepository.updateTask(TaskItemModelMapper().mapDataModel(it)).onEach { result ->
                when (result) {
                    is Response.Success -> {
                        result.data?.let { item ->

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
    }

    private fun addTaskFromDB(value: TaskItemModel?) = runBlocking<Unit> {

        value?.let {
            taskRepository.insertTask(TaskItemModelMapper().mapDataModel(it)).onEach { result ->
                when (result) {
                    is Response.Success -> {
                        result.data?.let { item ->

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
    }

    fun deleteTaskByIdFromDB(id: Int?) = runBlocking<Unit> {
        id?.let {
            taskRepository.deleteTaskById(id).onEach { result ->
                when (result) {
                    is Response.Success -> {
                        result.data?.let { item ->

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
    }

    private fun handleError() {
        //TODO("Not yet implemented")
    }

    private fun handleLoading() {
        //TODO("Not yet implemented")
    }

    private fun populateTask(item: List<TaskItem>) {
        item.forEach {
            //taskList.add( TaskItemMapper().mapDataModel(it))
        }
    }
}