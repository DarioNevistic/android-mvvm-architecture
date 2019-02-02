package com.example.dario.pcbakovic.di

import com.example.dario.pcbakovic.ui.catalogue.CatalogueFragment
import com.example.dario.pcbakovic.ui.home.HomeFragment
import com.example.dario.pcbakovic.ui.scanner.ScannerFragment
import com.example.dario.pcbakovic.ui.shop.ShopFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBindingModule {

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun bindCatalogueFragment(): CatalogueFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun bindShopFragment(): ShopFragment

    @ContributesAndroidInjector
    @FragmentScoped
    abstract fun bindScannerFragment(): ScannerFragment
}