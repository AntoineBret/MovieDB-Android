package com.hadeso.moviedb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.hadeso.moviedb.R
import com.hadeso.moviedb.ui.discovery.DiscoveryFragment
import com.hadeso.moviedb.utils.replaceFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class DiscoveryActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discovery)

        if (savedInstanceState == null) {
          replaceFragment(DiscoveryFragment.newInstance(), R.id.discovery_container)
        }
    }

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

  override fun onSupportNavigateUp(): Boolean {
    onBackPressed()
    return true
  }
}
