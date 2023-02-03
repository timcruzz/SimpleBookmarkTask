package com.timothy.simplebookmarktask.config

sealed class Constants {
     object ConfigIdentifier {
         const val IDENTIFIER_KEY = "IDENTIFIER_KEY"
        const val ADD_TASK_KEY = "ADD_TASK_KEY"
        const val UPDATE_TASK_KEY = "UPDATE_TASK_KEY"
    }

    object ScreenIdentifier{
        const val HOME_TASK_LIST_SCREEN = "HOME_TASK_LIST_SCREEN"
        const val CONFIG_TASK_LIST_SCREEN = "CONFIG_TASK_LIST_SCREEN"
    }
}