package com.example.memesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class UserSpaceActivity : AppCompatActivity() {
    companion object {
        val TAG = "UserSpaceActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_UserSpace)
    }
}