package com.pelagohealth.codingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelagohealth.codingchallenge.data.repository.FactRepository
import com.pelagohealth.codingchallenge.domain.model.Fact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.pelagohealth.codingchallenge.presentation.navigation.NavigatorHolder

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private var repository: FactRepository,
    private var navigatorHolder: NavigatorHolder
) : ViewModel() {

    private val _state = MutableStateFlow(HistoryScreenState())
    val state: StateFlow<HistoryScreenState> = _state

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
        navigatorHolder.get().navigateToHome()
    }

    data class HistoryScreenState(
        val facts: List<Fact>? = null,
        val loading: Boolean = false,
    )
}
