package com.pelagohealth.codingchallenge.presentation

import androidx.annotation.MainThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.pelagohealth.codingchallenge.domain.model.GetFactUseCase
import kotlinx.coroutines.flow.update
import com.pelagohealth.codingchallenge.presentation.navigation.NavigatorHolder

@HiltViewModel
class MainViewModel @Inject constructor(
    private var getFactUseCase: GetFactUseCase,
    private var navigatorHolder: NavigatorHolder,
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    init {
        fetchNewFact()
    }

    fun onUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            UiEvent.FetchNewFact -> fetchNewFact()
            UiEvent.NavigateToHistory -> navigateToHistory()
        }
    }

    sealed class UiEvent {
        object FetchNewFact : UiEvent()
        object NavigateToHistory : UiEvent()
    }

    fun navigateToHistory() {
        navigatorHolder.get().navigateToHistory()
    }

    fun fetchNewFact() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            getFactUseCase.execute().fold(
                onSuccess = { fact ->
                    _state.update { it.copy(currentFact = fact.text, loading = false) }
                },
                onFailure = {
                    _state.update {
                        it.copy(
                            currentFact = "Oops, please try again.",
                            loading = false
                        )
                    }
                }
            )
        }
    }
}

data class MainScreenState(
    val currentFact: String = "",
    val loading: Boolean = false,
)
