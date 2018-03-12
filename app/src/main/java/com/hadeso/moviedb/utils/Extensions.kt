package com.hadeso.moviedb.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by 77796 on 12-Mar-18.
 */
fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}