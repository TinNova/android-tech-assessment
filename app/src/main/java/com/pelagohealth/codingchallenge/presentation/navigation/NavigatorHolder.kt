package com.pelagohealth.codingchallenge.presentation.navigation

import androidx.navigation.NavController

/**
 * Holder Pattern (Lazy Initialization Pattern) implementation for Navigator.
 * 
 * This class solves the timing problem where ViewModels need Navigator,
 * but Navigator depends on NavController which is created later in the Compose lifecycle.
 * 
 * Lifecycle flow:
 * 1. App starts → NavigatorHolder created (empty)
 * 2. ViewModels created → Injected with NavigatorHolder
 * 3. Compose initializes → NavController created
 * 4. AppNavHost → Creates NavigatorImpl and calls setNavigator()
 * 5. ViewModels can now call get() to access Navigator
 *
 */
class NavigatorHolder {
    private var _navigator: Navigator? = null
    
    /**
     * Gets the Navigator instance.
     * @throws IllegalStateException if Navigator hasn't been initialized yet
     */
    fun get(): Navigator {
        return _navigator ?: error("Navigator not initialized. Call setNavigator() first.")
    }
    
    /**
     * Sets the Navigator instance. Should be called once when NavController is available.
     */
    fun setNavigator(navController: NavController) {
        this._navigator = NavigatorImpl(navController)
    }
}
