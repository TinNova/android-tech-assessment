package com.pelagohealth.codingchallenge.data

import android.content.Context
import androidx.room.Room
import com.pelagohealth.codingchallenge.data.datasource.local.FactDao
import com.pelagohealth.codingchallenge.data.datasource.local.FactDatabase
import com.pelagohealth.codingchallenge.data.datasource.rest.FactsRestApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * Hilt module that provides dependencies for the data layer.
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideFactsApi(okHttpClient: OkHttpClient): FactsRestApi =
        Retrofit.Builder()
            .baseUrl("https://uselessfacts.jsph.pl/api/v2/")
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                )
            )
            .build()
            .create(FactsRestApi::class.java)

    @Provides
    @Singleton
    fun provideFactDatabase(@ApplicationContext context: Context): FactDatabase =
        Room.databaseBuilder(
            context,
            FactDatabase::class.java,
            "fact_database"
        ).build()

    @Provides
    fun provideFactDao(database: FactDatabase): FactDao = database.factDao()
}
