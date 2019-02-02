package com.example.dario.pcbakovic.base

import com.example.dario.pcbakovic.util.NavigationManager

interface HasNavigationManager {

    fun provideNavigationManager(): NavigationManager
}