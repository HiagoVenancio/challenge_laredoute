package com.example.challenge.ui.navigation

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.challenge.ui.screens.MainScreen
import com.example.challenge.ui.screens.StartScreen
import com.example.challenge.ui.screens.WinnerScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object WinnerScreen : Screen("winner/{name}/{color}") {
        fun createRoute(name: String, color: String): String {
            val encodedColor = Uri.encode(color)
            return "winner/$name/$encodedColor"
        }
    }
    object FirstScreen : Screen("first_screen")
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.FirstScreen.route,
    ) {
        composable(Screen.FirstScreen.route) {
            StartScreen(navController)
        }

        composable(Screen.MainScreen.route) {
            MainScreen(navController)
        }

        composable(route = Screen.WinnerScreen.route,
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("color") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: "Unknown"
            val color = Uri.decode(backStackEntry.arguments?.getString("color") ?: "#FFFFFF")
            WinnerScreen(navController, name = name, color = color)
        }
    }
}
