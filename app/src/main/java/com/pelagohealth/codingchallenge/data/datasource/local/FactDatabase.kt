package com.pelagohealth.codingchallenge.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FactEntity::class], version = 1, exportSchema = false)
abstract class FactDatabase : RoomDatabase() {
    abstract fun factDao(): FactDao
}
