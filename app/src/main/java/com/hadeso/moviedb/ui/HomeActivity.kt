package com.hadeso.moviedb.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.hadeso.moviedb.R
import com.hadeso.moviedb.ui.home.HomeFragment
import com.hadeso.moviedb.utils.replaceFragment
import com.hadeso.moviedb.utils.startActivity
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector, NavigationView.OnNavigationItemSelectedListener {
  @Inject
  lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

  private lateinit var drawer: DrawerLayout
  private lateinit var toggle: ActionBarDrawerToggle

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    /**
     * Display home fragment
     */
    if (savedInstanceState == null) {
      replaceFragment(HomeFragment.newInstance(), R.id.main_container)
    }

    initDrawer()
    initToolbar()
  }

  override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> {
    return dispatchingAndroidInjector
  }

  private fun initToolbar() {
    val toolbar = findViewById<Toolbar>(R.id.main_toolbar)
    setSupportActionBar(toolbar)

    toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    drawer.addDrawerListener(toggle)
    toggle.syncState()

    actionBar?.setDisplayHomeAsUpEnabled(true)
    actionBar?.setDisplayShowTitleEnabled(false)
  }

  private fun initDrawer() {
    drawer = findViewById(R.id.drawer_layout)

    val navigationView: NavigationView = findViewById(R.id.nav_view)
    navigationView.setNavigationItemSelectedListener(this)
  }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      //discover
      R.id.nav_discover_movie -> startActivity<DiscoveryActivity>()
      R.id.nav_discover_tv -> Toast.makeText(this, "nav_discover_tv", Toast.LENGTH_SHORT).show()
      //movie
      R.id.nav_movie_popular -> Toast.makeText(this, "nav_movie_popular", Toast.LENGTH_SHORT).show()
      R.id.nav_movie_best_rated -> Toast.makeText(this, "nav_movie_best_rated", Toast.LENGTH_SHORT).show()
      R.id.nav_movie_shortly -> Toast.makeText(this, "nav_movie_shortly", Toast.LENGTH_SHORT).show()
      R.id.nav_movie_in_theaters -> Toast.makeText(this, "nav_movie_in_theaters", Toast.LENGTH_SHORT).show()
      //tv
      R.id.nav_tv_popular -> Toast.makeText(this, "nav_tv_popular", Toast.LENGTH_SHORT).show()
      R.id.nav_tv_best_rated -> Toast.makeText(this, "nav_tv_best_rated", Toast.LENGTH_SHORT).show()
      R.id.nav_tv_actually_broadcast -> Toast.makeText(this, "nav_tv_actually_broadcast", Toast.LENGTH_SHORT).show()
      R.id.nav_tv_today_broadcast -> Toast.makeText(this, "nav_tv_today_broadcast", Toast.LENGTH_SHORT).show()
      //artist

    }
    drawer.closeDrawer(GravityCompat.START)
    return true
  }

  override fun onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START)
    } else {
      super.onBackPressed()
    }
  }
}
