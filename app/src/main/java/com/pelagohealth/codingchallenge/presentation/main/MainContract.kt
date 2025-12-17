package com.pelagohealth.codingchallenge.presentation.main

interface MainContract {

    data class MainScreenState(
        val currentFact: String = "",
        val loading: Boolean = false,
    )

    sealed class UiEvent {
        object FetchNewFact : UiEvent()
        object NavigateToHistory : UiEvent()
    }
}