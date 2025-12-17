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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainContent(state, modifier) { viewModel.onUiEvent(it) }
}

@Composable
fun MainContent(
    state: MainScreenState,
    modifier: Modifier,
    uiAction: (MainViewModel.UiEvent) -> Unit
) {

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
                onClick = { uiAction(MainViewModel.UiEvent.FetchNewFact) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading
            ) {
                Text("More facts!")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { uiAction(MainViewModel.UiEvent.NavigateToHistory) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Show history")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainContent(
        state = MainScreenState(
            currentFact = "Cats sleep 70% of their lives.",
            loading = false
        ),
        modifier = Modifier,
        uiAction = {}
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenLoadingPreview() {
    MainContent(
        state = MainScreenState(
            currentFact = "",
            loading = true
        ),
        modifier = Modifier,
        uiAction = {}
    )
}
