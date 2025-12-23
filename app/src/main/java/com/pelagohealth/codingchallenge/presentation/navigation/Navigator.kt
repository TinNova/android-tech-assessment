package com.pelagohealth.codingchallenge.presentation.navigation

import androidx.navigation.NavController
import javax.inject.Inject

interface Navigator {
    fun setController(navController: NavController)
    fun navigate(destination: Destination)
    fun popBackStack()
    fun navigateUp()
}

class NavigatorImpl @Inject constructor() : Navigator {

    private var navController: NavController? = null

    override fun setController(navController: NavController) {
        this.navController = navController
    }

    override fun navigate(destination: Destination) {
        navController?.navigate(route = destination)
    }

    // What does this actually mean?? And how does it compare to navigateUp?
    override fun popBackStack() {
        navController?.popBackStack()
    }

    override fun navigateUp() {
        navController?.navigateUp()
    }

//    override fun clear() {
//        navController = null
//    }
}
