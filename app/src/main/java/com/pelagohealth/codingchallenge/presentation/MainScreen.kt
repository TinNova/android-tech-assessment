package com.pelagohealth.codingchallenge.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    //TODO: ViewModel used directly in screen, therefore can't be unitTested
    // Integration Test, but with Robolectric it's a UnitTest
    val state by viewModel.state.collectAsStateWithLifecycle()

    //TODO: Inject ViewModel with Hilt

    //TODO: Navigation should be injected into the ViewModel
    LaunchedEffect(navController) {
        viewModel.attachNavController(navController)
    }

    //TODO: Display a loading icon?
    //TODO: Screen bounces after tapping "More facts!"
    //TODO: Hard coded strings, but not a big deal...
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = state.currentFact,
            modifier = modifier
        )
        Button(onClick = { viewModel.fetchNewFact() }) {
            Text("More facts!")
        }
        Button(onClick = { viewModel.navigateToSecondScreen() }) {
            Text("Show history")
        }
    }
}
