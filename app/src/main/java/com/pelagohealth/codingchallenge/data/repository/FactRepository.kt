package com.pelagohealth.codingchallenge.data.repository

import com.pelagohealth.codingchallenge.data.datasource.local.FactDao
import com.pelagohealth.codingchallenge.data.datasource.local.FactEntity
import com.pelagohealth.codingchallenge.data.datasource.rest.APIFact
import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import com.pelagohealth.codingchallenge.domain.model.Fact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Repository providing random facts.
 */
class FactRepository @Inject constructor(
    private val api: FactsRestApi,
    private val factDao: FactDao
) {

    suspend fun get(): Result<APIFact> {
        return runCatching { api.getFact() }
            .onSuccess { fact ->
                factDao.addFactWithLimit(
                    FactEntity(
                        text = fact.text,
                        sourceUrl = fact.sourceUrl
                    )
                )
            }
    }

    fun getAllFacts(): Flow<List<Fact>> {
        return factDao.getAllMinusLatestFact().map { entities ->
            entities.map { entity ->
                Fact(text = entity.text, url = entity.sourceUrl)
            }
        }
    }

}
