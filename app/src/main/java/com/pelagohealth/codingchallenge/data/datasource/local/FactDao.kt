package com.pelagohealth.codingchallenge.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

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
