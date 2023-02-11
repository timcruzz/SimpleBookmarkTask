package com.timothy.simplebookmarktask.ui.details

import android.app.Application
import android.os.CountDownTimer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.timothy.simplebookmarktask.config.Constants
import com.timothy.simplebookmarktask.config.Response
import com.timothy.simplebookmarktask.data.db.TaskDatabase
import com.timothy.simplebookmarktask.data.db.entity.TaskItem
import com.timothy.simplebookmarktask.data.repository.TaskRepositoryImpl
import com.timothy.simplebookmarktask.domain.mapper.TaskItemMapper
import com.timothy.simplebookmarktask.domain.mapper.TaskItemModelMapper
import com.timothy.simplebookmarktask.domain.models.TaskItemModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class TaskDetailsScreenViewModel(
    application: Application,
    private val navController: NavHostController,
) : AndroidViewModel(application) {


    private val taskDatabase: TaskDatabase by lazy { TaskDatabase.invoke(application) }
    private val taskRepository by lazy { TaskRepositoryImpl(taskDatabase) }

    val taskItem: MutableState<TaskItemModel?> = mutableStateOf(null)
    val currentTime: MutableState<Long> = mutableStateOf(0L)
    val isTimerRunning: MutableState<Boolean> = mutableStateOf(false)

    val configIdentifier: MutableState<String?> =
        mutableStateOf(Constants.ConfigIdentifier.ADD_TASK_KEY)

    lateinit var countDownTimer: CountDownTimer
    var targetMinutesMillis: Long = 0
    var originalTargetMinutesMillis: Long = 0

    init {
        loadFromBackStack()
    }

    private fun loadFromBackStack() {
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<Int>(Constants.ConfigIdentifier.UPDATE_TASK_KEY)?.let {
                getTaskItemByIdFromDB(it)
            }
        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.get<String>(Constants.ConfigIdentifier.IDENTIFIER_KEY)?.let {
                configIdentifier.value = it
            }
    }


    fun startCountdownTimer(
        totalMinuteSet: Int
    ){
        // time count down for 30 seconds,
        targetMinutesMillis = totalMinuteSet * 10000L
        originalTargetMinutesMillis = targetMinutesMillis

        // int minutes = millis / 1000 / 60
        countDownTimer = object : CountDownTimer(targetMinutesMillis, 100L) {

            // Callback function, fired on regular interval
            override fun onTick(millisUntilFinished: Long) {
                isTimerRunning.value = true
                targetMinutesMillis = millisUntilFinished
                currentTime.value = millisUntilFinished
                // textView.setText("seconds remaining: " + millisUntilFinished / 1000)
            }

            // Callback function, fired
            // when the time is up
            override fun onFinish() {
                //  textView.setText("done!")
            }
        }.start()
    }

    fun pauseCountdownTimer(){
        isTimerRunning.value = false
        countDownTimer.cancel()
    }

    fun resetCountdownTimer(){
        targetMinutesMillis = originalTargetMinutesMillis
        currentTime.value = originalTargetMinutesMillis
        isTimerRunning.value = false
    }





    fun updateOrAddTaskDB(taskItem: TaskItemModel) {
        if (configIdentifier.value == Constants.ConfigIdentifier.UPDATE_TASK_KEY) {
            updateTaskFromDB(taskItem)
        } else {
            addTaskFromDB(taskItem)
        }
    }

     fun getTaskItemByIdFromDB(id: Int) = runBlocking<Unit> {
        taskRepository.getTaskById(id).onEach { result ->
            when (result) {
                is Response.Success -> {
                    result.data?.let { item ->
                        taskItem.value = TaskItemMapper().mapDataModel(item)

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
}