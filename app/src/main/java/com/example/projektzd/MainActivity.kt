package com.example.projektzd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar

import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.projektzd.fragments.databaseFragments.ListFragment
import com.example.projektzd.fragments.searchFragments.SearchFragment
import com.google.android.material.navigation.NavigationView
import com.example.projektzd.fragments.AuthorsFragment
import com.example.projektzd.fragments.HelpFragment
import com.example.projektzd.fragments.HomeFragment
import com.example.projektzd.fragments.favouriteFragments.CompletedListFragment

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawer = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                HomeFragment()
            ).addToBackStack("HomeFragment").commit()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            ).replace(
                R.id.fragment_container,
                HomeFragment()
            ).addToBackStack("HomeFragment").commit()
            R.id.nav_library -> supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            ).replace(
                R.id.fragment_container,
                ListFragment(
                    supportFragmentManager
                )
            ).addToBackStack("MyList").commit()
            R.id.nav_completed -> supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            ).replace(
                R.id.fragment_container,
                CompletedListFragment(
                    supportFragmentManager
                )
            ).addToBackStack("Completed").commit()
            R.id.nav_search -> supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            ).replace(
                R.id.fragment_container,
                SearchFragment(
                    supportFragmentManager
                )
            ).addToBackStack("SearchFragment").commit()
            R.id.nav_help -> supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            ).replace(
                R.id.fragment_container,
                HelpFragment()
            ).addToBackStack("HelpFragment").commit()
            R.id.nav_info -> supportFragmentManager.beginTransaction().setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            ).replace(
                R.id.fragment_container,
                AuthorsFragment()
            ).addToBackStack("AuthorsFragment").commit()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            val fm = supportFragmentManager
            if (fm.backStackEntryCount > 0) {
                fm.popBackStack()
            } else {
                super.onBackPressed()
            }
        }
    }
}


