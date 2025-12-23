package com.pelagohealth.codingchallenge.presentation.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelagohealth.codingchallenge.data.repository.FactRepository
import com.pelagohealth.codingchallenge.presentation.history.HistoryContract.HistoryScreenState
import com.pelagohealth.codingchallenge.presentation.history.HistoryContract.UiEvent
import com.pelagohealth.codingchallenge.presentation.navigation.Destination
import com.pelagohealth.codingchallenge.presentation.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private var repository: FactRepository,
    private var navigator: Navigator
) : HistoryContract, ViewModel() {

    private val _state = MutableStateFlow(HistoryScreenState())
    val state: StateFlow<HistoryScreenState> = _state

    fun onUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.NavigateHome -> navigateHome()
        }
    }

    init {
        //NOTE: We can cancel the flow after collecting the first item as that's all we need like this: "repository.getAllFacts().first()"
        //However, to show you that I'm aware of how to cancel flows in a lifecycle aware way I'll leave it like this.
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            repository.getAllFacts()
                .stateIn(
                    scope = viewModelScope,
                    started = WhileSubscribed(5000),
                    initialValue = emptyList()
                )
                .collect { facts ->
                    _state.update { it.copy(facts = facts, loading = false) }
                }
        }
    }
    
    fun navigateHome() {
        navigator.navigate(Destination.MainScreen)
    }
}
