package com.timothy.simplebookmarktask.ui.event

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavHostController

class ConfigureTaskViewModelFactory(
    private val application: Application,
    private val navController: NavHostController,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ConfigureTaskScreenViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return ConfigureTaskScreenViewModel(
                application,
                navController
            ) as T
        }
        throw IllegalArgumentException("Unknown VM Class")
    }

}