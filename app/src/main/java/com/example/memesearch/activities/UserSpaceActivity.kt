package com.example.memesearch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.backendless.Backendless
import com.example.memesearch.R
import com.example.memesearch.fragments.GalleryFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_userspace.*
import kotlinx.android.synthetic.main.nav_drawer.*
import kotlinx.android.synthetic.main.nav_drawer.view.*

class UserSpaceActivity : AppCompatActivity() {
    companion object {
        val TAG = "UserSpaceActivity"
    }

    private lateinit var navController : NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userspace)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.userSpaceNavigationFragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(activityUserspace_navView_userSettings, navController)
    }
}