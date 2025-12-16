package com.pelagohealth.codingchallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pelagohealth.codingchallenge.data.repository.FactRepository
import com.pelagohealth.codingchallenge.domain.model.Fact
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.navigation.NavController

@HiltViewModel
class MainViewModel @Inject constructor(
    private var repository: FactRepository
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
        GlobalScope.launch { //TODO: Use ViewModelScope
            fetchNewFact()
        }
    }

    fun navigateToSecondScreen() {
        //TODO: Make this type-Safe instead of taking a string
        navController.navigate("history")
    }

    fun fetchNewFact() {
        //TODO: Dispatchers.Main can be removed when using ViewModelScope
        //TODO: Remove CoroutineScope and make fetchNewFact a suspend function
        CoroutineScope(Dispatchers.Main).launch {
            _state.value = MainScreenState(loading = true)
            runCatching { repository.get() }
                .onSuccess { fact ->
                    //TODO: loading not reset to false
                    _state.value = MainScreenState(current = fact, loading = false)
                }
                .onFailure { e ->
                    //TODO: loading not reset to false
                    println(e)
                }
        }
    }

    data class MainScreenState(
        val current: Fact? = null,
        val loading: Boolean = false,
    )
}