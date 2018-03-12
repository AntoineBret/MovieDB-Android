package com.hadeso.moviedb.utils

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager

/**
 * Created by 77796 on 12-Mar-18.
 */
/**
 * A value holder that automatically clears the reference if the Fragment's view is destroyed.
 * @param <T>
</T> */
class AutoClearedValue<T>(fragment: Fragment, private var value: T?) {
    init {
        val fragmentManager = fragment.fragmentManager
        fragmentManager?.registerFragmentLifecycleCallbacks(
                object : FragmentManager.FragmentLifecycleCallbacks() {
                    override fun onFragmentViewDestroyed(fm: FragmentManager?, f: Fragment?) {
                        if (f === fragment) {
                            this@AutoClearedValue.value = null
                            fragmentManager.unregisterFragmentLifecycleCallbacks(this)
                        }
                    }
                }, false)
    }

    fun get(): T? {
        return value
    }
}