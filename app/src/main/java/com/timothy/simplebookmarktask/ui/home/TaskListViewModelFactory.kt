package com.timothy.simplebookmarktask.ui.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class TaskListViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TaskListScreenViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return TaskListScreenViewModel(
                application
            ) as T
        }
        throw IllegalArgumentException("Unknown VM Class")
    }

}