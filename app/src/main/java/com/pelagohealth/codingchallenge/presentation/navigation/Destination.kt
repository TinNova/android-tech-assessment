package com.pelagohealth.codingchallenge.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object HistoryScreen : Destination
    @Serializable
    data object MainScreen : Destination
}