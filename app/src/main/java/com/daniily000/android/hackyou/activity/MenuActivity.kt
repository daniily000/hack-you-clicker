package com.daniily000.android.hackyou.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.daniily000.android.hackyou.R

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
    }

    fun launchGame(v: View) {
        startActivity(Intent(this, ChapterManagerActivity::class.java))
    }
}
