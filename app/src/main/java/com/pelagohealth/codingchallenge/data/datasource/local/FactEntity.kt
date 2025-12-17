package com.pelagohealth.codingchallenge.data.datasource.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facts")
data class FactEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val sourceUrl: String,
    val timestamp: Long = System.currentTimeMillis()
)
