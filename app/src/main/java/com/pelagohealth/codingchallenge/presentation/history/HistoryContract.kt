package com.pelagohealth.codingchallenge.presentation.history

import com.pelagohealth.codingchallenge.domain.model.Fact

interface HistoryContract {

    data class HistoryScreenState(
        val facts: List<Fact>? = null,
        val loading: Boolean = false,
    )

    sealed class UiEvent {
        object NavigateHome: UiEvent()
    }


}