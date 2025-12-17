package com.pelagohealth.codingchallenge.presentation.navigation

import androidx.navigation.NavController

class NavigatorImpl(
    private val navController: NavController
) : Navigator {

    override fun navigateToHistory() {
        navController.navigate(Routes.HISTORY.name)
    }

    override fun navigateToHome() {
        navController.navigate(Routes.HOME.name)
    }

    enum class Routes {
        HOME, HISTORY
    }
}
