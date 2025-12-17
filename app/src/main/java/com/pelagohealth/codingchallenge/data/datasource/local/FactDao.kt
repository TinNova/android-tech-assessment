package com.pelagohealth.codingchallenge.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//NOTE:
/*
 * I'm using Room to save the facts for the HistoryScreen. I'm saving 11 Facts where the latest Fact is the current Fact, this is required to retain the state of the app in case of system death.
 * * We could also save the Current Facts ID in SavedStateHandle and fetch it
 *
 * An alternative solution to Room is SharedPref, we could store the IDs of the Facts and fetch them from the network when visiting the History Screen
 * We could also store the facts in-Memory as the criteria didn't state what happens when the app is terminated
 *
 * I thought Room was the best option because:
 * * It reduces network usage
 * * * With network usage reduced network errors are also reduced, reducing complexity and improving user experience
 * * It's easy to query and customise (more functionality than SharePref/DataStore)
 * * Hits two birds with one stone, store facts and restore state after a system death
 * * It's a well known API
 */
@Dao
interface FactDao {
    
    @Query("SELECT * FROM facts ORDER BY timestamp DESC LIMIT -1 OFFSET 1")
    fun getAllMinusLatestFact(): Flow<List<FactEntity>>
    
    @Insert
    suspend fun insert(fact: FactEntity)
    
    @Query("SELECT COUNT(*) FROM facts")
    suspend fun getFactCount(): Int
    
    @Query("DELETE FROM facts WHERE id IN (SELECT id FROM facts ORDER BY timestamp ASC LIMIT 1)")
    suspend fun deleteOldest()
    
    /**
     * Adds a fact and ensures only 11 facts are kept by deleting the oldest if necessary.
     */
    suspend fun addFactWithLimit(fact: FactEntity) {
        insert(fact)
        val count = getFactCount()
        if (count > MAX_FACTS) {
            deleteOldest()
        }
    }

    private companion object {
        const val MAX_FACTS = 11
    }
}
