package com.pelagohealth.codingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.navigation.NavController
import com.pelagohealth.codingchallenge.domain.model.GetFactUseCase
import kotlinx.coroutines.flow.update

@HiltViewModel
class MainViewModel @Inject constructor(
    private var getFactUseCase: GetFactUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state
    private lateinit var navController: NavController

    //TODO: Mechanism to save and load facts doesn't exist
    //TODO: Navigation could be injected into the ViewModel
    //Benefit: This adheres to unilateral flow
    fun attachNavController(controller: NavController) {
        this.navController = controller
    }

    init {
        fetchNewFact()
    }

    fun navigateToSecondScreen() {
        //TODO: Make this type-Safe instead of taking a string
        navController.navigate("history")
    }

    fun fetchNewFact() {
        //TODO: Dispatchers.Main can be removed when using ViewModelScope
        //TODO: Remove CoroutineScope and make fetchNewFact a suspend function
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
