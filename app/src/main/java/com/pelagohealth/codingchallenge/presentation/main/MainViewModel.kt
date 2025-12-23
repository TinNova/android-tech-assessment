package com.pelagohealth.codingchallenge.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelagohealth.codingchallenge.domain.model.Fact
import com.pelagohealth.codingchallenge.domain.model.FactInMemoryCache
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.pelagohealth.codingchallenge.domain.model.GetFactUseCase
import com.pelagohealth.codingchallenge.presentation.main.MainContract.MainScreenState
import com.pelagohealth.codingchallenge.presentation.main.MainContract.UiEvent
import com.pelagohealth.codingchallenge.presentation.navigation.Destination
import com.pelagohealth.codingchallenge.presentation.navigation.Navigator
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel @Inject constructor(
    private var getFactUseCase: GetFactUseCase,
    private var factInMemoryCache: FactInMemoryCache,
    private var navigator: Navigator,
) : MainContract, ViewModel() {

    //NOTE: Currently we're not retaining the state of the app in the event of a system death (Android OS killing the app to save memory)
    // This can be achieved by saving a boolean in SavedStateHandle, if true (a system death occurred) we can display the last saved Fact from Room (this is why I save 11 Facts in Room instead of 10, to preempt the need for it in this circumstance), else continue as normal.
    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state

    init {
        factInMemoryCache.cache.value?.let {
            displayFact(it)
        } ?: fetchNewFact()
    }

    fun onUiEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            UiEvent.FetchNewFact -> fetchNewFact()
            UiEvent.NavigateToHistory -> navigateToHistory()
        }
    }

    private fun navigateToHistory() {
        navigator.navigate(Destination.HistoryScreen)
    }

    private fun fetchNewFact() {
        viewModelScope.launch {
            _state.update { it.copy(loading = true) }
            getFactUseCase.execute().fold(
                onSuccess = { fact ->
                    factInMemoryCache.updateCache(fact)
                    displayFact(fact)
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

    private fun displayFact(fact: Fact) {
        _state.update { it.copy(currentFact = fact.text, loading = false) }
    }
}
