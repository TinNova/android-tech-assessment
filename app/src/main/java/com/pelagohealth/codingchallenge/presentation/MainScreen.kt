package com.pelagohealth.codingchallenge.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (state.loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            Text(
                text = state.currentFact,
                modifier = Modifier.align(Alignment.Center)
            )
        }
        
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { viewModel.fetchNewFact() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading
            ) {
                Text("More facts!")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { viewModel.navigateToSecondScreen() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Show history")
            }
        }
    }
}
