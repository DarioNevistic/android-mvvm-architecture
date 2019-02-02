package com.example.dario.pcbakovic.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import com.example.dario.pcbakovic.R
import com.example.dario.pcbakovic.base.BaseFragment
import com.example.dario.pcbakovic.ui.catalogue.CatalogueFragment
import com.example.dario.pcbakovic.ui.home.HomeFragment
import com.example.dario.pcbakovic.ui.scanner.ScannerFragment
import com.example.dario.pcbakovic.ui.shop.ShopFragment
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : DaggerAppCompatActivity() {


    val fragment1: BaseFragment = HomeFragment()
    val fragment2: BaseFragment = CatalogueFragment()
    val fragment3: BaseFragment = ShopFragment()
    val fragment4: BaseFragment = ScannerFragment()
    val fm = supportFragmentManager
    var active = fragment1

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                fm.beginTransaction().hide(active).show(fragment1).commit()
                active = fragment1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_catalogue -> {
                fm.beginTransaction().hide(active).show(fragment2).commit()
                active = fragment2
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_shoping_list -> {
                fm.beginTransaction().hide(active).show(fragment3).commit()
                active = fragment3
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_scanner -> {
                fm.beginTransaction().hide(active).show(fragment4).commit()
                active = fragment4
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            fm.beginTransaction().add(R.id.container, fragment4, "4").hide(fragment4).commit();
            fm.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
            fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
            fm.beginTransaction().add(R.id.container,fragment1, "1").commit();
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
