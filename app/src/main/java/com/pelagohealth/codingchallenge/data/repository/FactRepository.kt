package com.pelagohealth.codingchallenge.data.repository

import com.pelagohealth.codingchallenge.data.datasource.rest.APIFact
import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import com.pelagohealth.codingchallenge.domain.model.Fact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repository providing random facts.
 */
class FactRepository @Inject constructor(
    private val api: FactsRestApi
) {
    
    suspend fun get(): Fact = withContext(Dispatchers.Default) {
        val dto: APIFact = api.getFact()
        //TODO: No error handling
        Fact(text = dto.text, url = dto.sourceUrl)
    }

}