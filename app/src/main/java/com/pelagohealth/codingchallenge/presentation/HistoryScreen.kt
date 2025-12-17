package com.pelagohealth.codingchallenge.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.pelagohealth.codingchallenge.domain.model.Fact

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    modifier: Modifier = Modifier
) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    HistoryContent(state, modifier) { viewModel.onUiEvent(it) }

}

@Composable
fun HistoryContent(
    state: HistoryViewModel.HistoryScreenState,
    modifier: Modifier,
    uiAction: (HistoryViewModel.UiEvent) -> Unit
) {
    BackHandler {
        uiAction(HistoryViewModel.UiEvent.NavigateHome)
    }

    Box(modifier = modifier.fillMaxSize()) {
        if (state.loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                item {
                    Text(
                        text = "Fact History",
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                state.facts?.let { facts ->
                    if (facts.isEmpty()) {
                        item {
                            Text(
                                text = "No facts saved yet",
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    } else {
                        items(facts) { fact ->
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
                                    Text(
                                        text = fact.text,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Button(
            onClick = { uiAction(HistoryViewModel.UiEvent.NavigateHome) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenPreview() {
    HistoryContent(
        state = HistoryViewModel.HistoryScreenState(
            facts = listOf(
                Fact(text = "Cats sleep 70% of their lives.", url = ""),
                Fact(text = "Dogs have been domesticated for over 15,000 years.", url = ""),
                Fact(text = "Octopuses have three hearts.", url = "")
            ),
            loading = false
        ),
        modifier = Modifier,
        uiAction = {}
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenEmptyPreview() {
    HistoryContent(
        state = HistoryViewModel.HistoryScreenState(
            facts = emptyList(),
            loading = false
        ),
        modifier = Modifier,
        uiAction = {}
    )
}

@Preview(showBackground = true)
@Composable
fun HistoryScreenLoadingPreview() {
    HistoryContent(
        state = HistoryViewModel.HistoryScreenState(
            facts = null,
            loading = true
        ),
        modifier = Modifier,
        uiAction = {}
    )
}
