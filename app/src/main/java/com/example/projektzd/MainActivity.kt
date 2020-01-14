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
import com.example.projektzd.database.DatabaseHelper

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawer: DrawerLayout
    private val dbHelper = DatabaseHelper(this)

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
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_library -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                ListFragment(
                    supportFragmentManager,
                    dbHelper
                )
            ).addToBackStack("MyList").commit()
            R.id.nav_search -> supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container,
                SearchFragment(
                    supportFragmentManager,
                    dbHelper
                )
            ).addToBackStack("SearchFragment").commit()
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


