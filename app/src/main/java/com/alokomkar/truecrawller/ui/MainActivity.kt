package com.alokomkar.truecrawller.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alokomkar.truecrawller.R

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

}
