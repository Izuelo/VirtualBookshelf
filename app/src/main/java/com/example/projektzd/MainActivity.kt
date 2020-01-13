package com.example.projektzd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.example.projektzd.api.RecyclerAdapter
import com.example.projektzd.fragments.AddBookFragment
import com.example.projektzd.fragments.MyListFragment
import com.example.projektzd.fragments.SearchFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity(),NavigationView.OnNavigationItemSelectedListener  {

    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var drawer: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar=findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawer=findViewById(R.id.drawer_layout)

        val navigationView: NavigationView =findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close)

        drawer.addDrawerListener(toggle)
        toggle.syncState()
//        val response = GetResponse()

//        recyclerAdapter = RecyclerAdapter()
//        findViewById<RecyclerView>(R.id.books_list).adapter = recyclerAdapter
//        findViewById<RecyclerView>(R.id.books_list).addItemDecoration(
//            DividerItemDecoration(
//                this,
//                DividerItemDecoration.VERTICAL
//            )
//        )
//
//        response.getBooks().observe(this, Observer {
//            it?.let {
//                recyclerAdapter.setBooks(it)
//            }
//        })
    }

    override fun onNavigationItemSelected(item: MenuItem ): Boolean {
        when(item.itemId){
            R.id.nav_library->supportFragmentManager.beginTransaction().replace(R.id.fragment_container, MyListFragment()).commit()
            R.id.nav_search->supportFragmentManager.beginTransaction().replace(R.id.fragment_container, SearchFragment()).commit()
            R.id.nav_add->supportFragmentManager.beginTransaction().replace(R.id.fragment_container, AddBookFragment()).commit()
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }
    }

}


