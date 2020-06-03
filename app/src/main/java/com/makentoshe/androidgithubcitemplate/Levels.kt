package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_levels.*
import kotlinx.android.synthetic.main.activity_teams.*

class Levels : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)
        imageView8.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        imageView9.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        imageView10.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        textView17.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        textView18.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        textView19.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        button1.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
            startActivity(intent)
            finish()
        }
    }
}