package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_levels.*

class Levels : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)
        easyLevelIcon.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        middleLevelIcon.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        hardLevelIcon.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        easyLevelText.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        middleLevelText.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        hardLevelText.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            startActivity(intent)
        }
        backButton.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
            startActivity(intent)
            finish()
        }
    }
}