package com.linole.diefamilie

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.linole.diefamilie.screens.account.AccountScreen
import com.linole.diefamilie.screens.dashboard.DashboardScreen

internal enum class Screen(val route: String) {
    Dashboard("dashboard"),
    Account("account"),
}

@Composable
internal fun AppNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        addHomeScreen(navController)
        addAccountScreen(navController)
    }
}

private fun NavGraphBuilder.addHomeScreen(navController: NavController) {
    composable(Screen.Dashboard.route) {
        DashboardScreen()
    }
}

private fun NavGraphBuilder.addAccountScreen(navController: NavController) {
    composable(Screen.Account.route) {
        AccountScreen(emptyList())
    }
}