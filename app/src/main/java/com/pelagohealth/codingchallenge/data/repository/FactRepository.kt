package com.pelagohealth.codingchallenge.data.repository

import com.pelagohealth.codingchallenge.data.datasource.rest.APIFact
import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi

import javax.inject.Inject

/**
 * Repository providing random facts.
 */
class FactRepository @Inject constructor(
    private val api: FactsRestApi
) {

    suspend fun get(): APIFact {
        return api.getFact()
    }

}
