package com.example.memesearch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.backendless.Backendless
import com.example.memesearch.Constants
import com.example.memesearch.R


class LoginActivity : AppCompatActivity() {

    companion object {
        var EXTRA_USERNAME = "username"
        var EXTRA_PASSWORD = "password"
    }

    private lateinit var navController : NavController
    private lateinit var navHostFragment : NavHostFragment
    lateinit var username: String
    lateinit var password: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val TAG = "LoginActivity"


        // initialize backendless
        Backendless.initApp(this, Constants.APP_ID, Constants.API_KEY)


        navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        // back button in teh action bar known as teh "up button"
        //NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }


}