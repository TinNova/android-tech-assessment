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

@Composable
fun AppNavHost(
    mainViewModel: MainViewModel,
    historyScreenViewModel: HistoryViewModel,
    navigator: Navigator,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    
    // Initialize the Navigator with the NavController
    LaunchedEffect(navController) {
        navigator.setController(navController)
    }
    
    NavHost(
        navController = navController,
        startDestination = Destination.MainScreen
    ) {
        composable<Destination.MainScreen> {
            MainScreen(
                viewModel = mainViewModel,
                modifier = modifier,
            )
        }
        composable<Destination.HistoryScreen> {
            HistoryScreen(
                viewModel = historyScreenViewModel,
                modifier = modifier
            )
        }
    }
}

