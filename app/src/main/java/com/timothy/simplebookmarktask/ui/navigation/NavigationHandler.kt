package com.timothy.simplebookmarktask.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.timothy.simplebookmarktask.config.Constants
import com.timothy.simplebookmarktask.ui.home.TaskListScreen

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
        }
    )
}