package com.pelagohealth.codingchallenge.presentation.navigation

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.Lazy
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {
    
    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder {
        return NavigatorHolder()
    }

}
