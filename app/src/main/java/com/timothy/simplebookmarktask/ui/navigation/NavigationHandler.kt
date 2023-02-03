package com.timothy.simplebookmarktask.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timothy.simplebookmarktask.config.Constants
import com.timothy.simplebookmarktask.domain.models.TaskItemModel
import com.timothy.simplebookmarktask.ui.event.ConfigureTaskScreen
import com.timothy.simplebookmarktask.ui.home.TaskListScreen
import com.timothy.simplebookmarktask.utilities.setCurrentBackstackSavedContent

@Composable
fun NavigationHandler(
    navController: NavHostController
) {
    val startScreen by remember { mutableStateOf(Constants.ScreenIdentifier.HOME_TASK_LIST_SCREEN) }

    NavHost(
        navController = navController,
        startDestination = startScreen,
        builder = {
            composable(
                route = Constants.ScreenIdentifier.HOME_TASK_LIST_SCREEN,
                content = {
                    TaskListScreen(navController = navController)
                }
            )

            composable(
                route = Constants.ScreenIdentifier.CONFIG_TASK_LIST_SCREEN,
                content = {
                    ConfigureTaskScreen (navController = navController)
                }
            )
        }
    )
}

fun navigateToConfigTaskListScreen(
    navController: NavController,
    configIdentifier: String?,
    taskItem: TaskItemModel? = null
){
    navController.setCurrentBackstackSavedContent(
        Constants.ConfigIdentifier.IDENTIFIER_KEY,
        configIdentifier
    )

    navController.setCurrentBackstackSavedContent(
        Constants.ConfigIdentifier.UPDATE_TASK_KEY,
        taskItem
    )

    navController.navigate(Constants.ScreenIdentifier.CONFIG_TASK_LIST_SCREEN)
}