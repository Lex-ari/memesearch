package com.example.memesearch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.memesearch.R

class UserSpaceActivity : AppCompatActivity() {
    companion object {
        val TAG = "UserSpaceActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userspace)
    }
}