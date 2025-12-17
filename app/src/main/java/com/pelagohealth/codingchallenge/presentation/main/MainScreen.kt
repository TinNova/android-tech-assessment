package com.pelagohealth.codingchallenge.presentation.main

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
import com.pelagohealth.codingchallenge.presentation.main.MainContract.MainScreenState
import com.pelagohealth.codingchallenge.presentation.main.MainContract.UiEvent

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
    uiAction: (UiEvent) -> Unit
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
                onClick = { uiAction(UiEvent.FetchNewFact) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.loading
            ) {
                Text("More facts!")
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { uiAction(UiEvent.NavigateToHistory) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Show history")
            }
        }
    }
}

//NOTE: By separating the MainContent from the MainScreen and by removing the NavController & ViewModel from the MainContent we can now create Previews
// Furthermore, we can now create Compose Tests (Compose Unit Test with Robolectric.).
@Preview(showBackground = true)
@Composable
fun MainContentPreview() {
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
fun MainContentLoadingPreview() {
    MainContent(
        state = MainScreenState(
            currentFact = "",
            loading = true
        ),
        modifier = Modifier,
        uiAction = {}
    )
}
