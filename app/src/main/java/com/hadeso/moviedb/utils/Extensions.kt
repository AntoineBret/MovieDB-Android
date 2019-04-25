package com.hadeso.moviedb.utils

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

/**
 * startActivity Intent
 * usage :
 * startActivity<YourActivity>()
 */
inline fun <reified T : Activity> Activity.startActivity() {
  startActivity(Intent(this, T::class.java))
}

/**
 * fragmentTransaction
 * usage >
 * addFragment(YourFragment.newInstance(), R.id.your_container)
 * replaceFragment(YourFragment.newInstance(), R.id.your_container)
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
  beginTransaction().func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int){
  supportFragmentManager.inTransaction { add(frameId, fragment) }
}


fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
  supportFragmentManager.inTransaction{replace(frameId, fragment)}
}
