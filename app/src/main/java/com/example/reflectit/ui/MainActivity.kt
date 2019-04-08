package com.example.reflectit.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.onNavDestinationSelected
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.reflectit.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar?)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)


        NavigationUI.setupActionBarWithNavController(this, navController)
        supportActionBar!!.setTitle(getString(R.string.mainActivityTitle))

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
//        return NavigationUI.navigateUp(null, navController)
    }

    override fun onBackPressed() {
        navController.navigateUp()
    }

}
