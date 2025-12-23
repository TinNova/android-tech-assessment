package com.pelagohealth.codingchallenge.domain.model

import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface InMemoryCache<T> {

    val cache: StateFlow<T?>

    suspend fun updateCache(newData: T)
}