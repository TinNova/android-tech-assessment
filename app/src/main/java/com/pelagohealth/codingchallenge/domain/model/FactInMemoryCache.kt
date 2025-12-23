package com.pelagohealth.codingchallenge.domain.model

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FactInMemoryCache @Inject constructor() : InMemoryCache<Fact> {

    private val _cache = MutableStateFlow<Fact?>(null)
    override val cache: StateFlow<Fact?> = _cache.asStateFlow()

    override suspend fun updateCache(newData: Fact) {
        _cache.update { newData }
    }
}