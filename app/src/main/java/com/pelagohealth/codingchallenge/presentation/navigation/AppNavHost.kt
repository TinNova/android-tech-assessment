package com.pelagohealth.codingchallenge.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pelagohealth.codingchallenge.presentation.history.HistoryScreen
import com.pelagohealth.codingchallenge.presentation.history.HistoryViewModel
import com.pelagohealth.codingchallenge.presentation.main.MainScreen
import com.pelagohealth.codingchallenge.presentation.main.MainViewModel
import com.pelagohealth.codingchallenge.presentation.navigation.NavigatorImpl.Routes.HISTORY
import com.pelagohealth.codingchallenge.presentation.navigation.NavigatorImpl.Routes.HOME

@Composable
fun AppNavHost(
    mainViewModel: MainViewModel,
    historyScreenViewModel: HistoryViewModel,
    navigatorHolder: NavigatorHolder,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    
    // Initialize the Navigator with the NavController
    LaunchedEffect(navController) {
        navigatorHolder.setNavigator(navController)
    }
    
    NavHost(
        navController = navController,
        startDestination = HOME.name
    ) {
        composable(route = HOME.name) {
            MainScreen(
                viewModel = mainViewModel,
                modifier = modifier,
            )
        }
        composable(route = HISTORY.name) {
            HistoryScreen(
                viewModel = historyScreenViewModel,
                modifier = modifier
            )
        }
    }
}

