package com.timothy.simplebookmarktask.config

sealed class Constants {

    object Timer {
        const val ACTION_SERVICE_START = "ACTION_SERVICE_START"
        const val ACTION_SERVICE_STOP = "ACTION_SERVICE_STOP"
        const val ACTION_SERVICE_CANCEL = "ACTION_SERVICE_CANCEL"

        const val STOPWATCH_STATE = "STOPWATCH_STATE"

        const val NOTIFICATION_CHANNEL_ID = "STOPWATCH_NOTIFICATION_ID"
        const val NOTIFICATION_CHANNEL_NAME = "STOPWATCH_NOTIFICATION"
        const val NOTIFICATION_ID = 10

        const val CLICK_REQUEST_CODE = 100
        const val CANCEL_REQUEST_CODE = 101
        const val STOP_REQUEST_CODE = 102
        const val RESUME_REQUEST_CODE = 103
    }

     object ConfigIdentifier {
         const val IDENTIFIER_KEY = "IDENTIFIER_KEY"
        const val ADD_TASK_KEY = "ADD_TASK_KEY"
        const val UPDATE_TASK_KEY = "UPDATE_TASK_KEY"
    }

    object ScreenIdentifier{
        const val HOME_TASK_LIST_SCREEN = "HOME_TASK_LIST_SCREEN"
        const val CONFIG_TASK_LIST_SCREEN = "CONFIG_TASK_LIST_SCREEN"
        const val TASK_DETAILS_SCREEN = "TASK_DETAILS_SCREEN"
    }
}