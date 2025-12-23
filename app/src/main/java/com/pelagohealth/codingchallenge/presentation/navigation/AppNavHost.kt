package com.pelagohealth.codingchallenge.presentation.navigation

import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pelagohealth.codingchallenge.presentation.history.HistoryScreen
import com.pelagohealth.codingchallenge.presentation.history.HistoryViewModel
import com.pelagohealth.codingchallenge.presentation.main.MainScreen
import com.pelagohealth.codingchallenge.presentation.main.MainViewModel
import kotlin.getValue

@Composable
fun AppNavHost(
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
            val mainVM: MainViewModel = hiltViewModel()
            MainScreen(
                viewModel = mainVM,
                modifier = modifier,
            )
        }
        composable<Destination.HistoryScreen> {
            val historyVM: HistoryViewModel = hiltViewModel()
            HistoryScreen(
                viewModel = historyVM,
                modifier = modifier
            )
        }
    }
}

