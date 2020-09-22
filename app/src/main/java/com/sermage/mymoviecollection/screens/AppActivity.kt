package com.sermage.mymoviecollection.screens

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sermage.mymoviecollection.R


class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_search, R.id.navigation_favorites
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id==R.id.movieDetailsFragment || destination.id==R.id.TVShowDetailsFragment){
                navView.visibility= View.GONE
            }else{
                navView.visibility=View.VISIBLE
            }
            if(destination.id==R.id.navigation_search || destination.id==R.id.searchingFragment){
                supportActionBar?.hide()
            }else{
                supportActionBar?.show()
            }
        }
        supportActionBar?.setBackgroundDrawable(ContextCompat.getDrawable(this,R.color.background_darkGrey))
        supportActionBar?.elevation= 0F

    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp() || super.onSupportNavigateUp()
    }
}