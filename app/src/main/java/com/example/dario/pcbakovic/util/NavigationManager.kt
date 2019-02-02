package com.example.dario.pcbakovic.util

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager


class NavigationManager(private val mFragmentManager: FragmentManager,private val container: Int) {

    init {
        mFragmentManager.addOnBackStackChangedListener{
            navigationListener?.invoke()
        }
    }

    val isRootFragmentVisible: Boolean
        get() = mFragmentManager.backStackEntryCount <= 1


    /**
     * Listener interface for navigation events.
     */
    var navigationListener:(()->Unit)?=null


    /**
     * Displays the next fragment
     *
     * @param fragment
     */
    fun open(fragment: Fragment) {
        openFragment(fragment, true, false)

    }

    private fun openFragment(fragment: Fragment, addToBackStack: Boolean, isRoot: Boolean) {
        val fragTransaction = mFragmentManager.beginTransaction()

        if (isRoot)
            fragTransaction.replace(container, fragment, "ROOT")
        else
            fragTransaction.replace(container, fragment)

        if (addToBackStack)
            fragTransaction.addToBackStack(fragment.toString())
        fragTransaction.commit()
    }

    /**
     * pops every fragment and starts the given fragment as a new one.
     *
     * @param fragment
     */
    fun openAsRoot(fragment: Fragment) {
        popEveryFragment()
        openFragment(fragment, false, true)
    }


    /**
     * Pops all the queued fragments
     */
    private fun popEveryFragment() {
        mFragmentManager.popBackStackImmediate("ROOT", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }


    fun navigateBack():Boolean {
        if (mFragmentManager.backStackEntryCount==0) {
            return false
        } else {
            mFragmentManager.popBackStackImmediate()
            return true
        }
    }


}