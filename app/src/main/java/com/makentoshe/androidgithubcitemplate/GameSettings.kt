package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_game_settings.*
import kotlinx.android.synthetic.main.activity_teams.*

class GameSettings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_settings)
        textView6.setOnClickListener {
            val intent = Intent(this, Levels::class.java)
            startActivity(intent)
        }
        button4.setOnClickListener {
            val intent = Intent(this, Teams::class.java)
            startActivity(intent)
        }
    }
}