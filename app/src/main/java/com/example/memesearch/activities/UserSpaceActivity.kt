package com.example.memesearch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.memesearch.R
import kotlinx.android.synthetic.main.activity_userspace.*

class UserSpaceActivity : AppCompatActivity() {
    companion object {
        val TAG = "UserSpaceActivity"
    }

    private lateinit var navController : NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userspace)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.userSpaceNavigationFragment) as NavHostFragment
        navController = navHostFragment.navController

        NavigationUI.setupWithNavController(activityUserspace_navView_userSettings, navController)
    }
}