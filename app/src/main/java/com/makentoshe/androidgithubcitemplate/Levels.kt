package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_levels.*

class Levels : AppCompatActivity() {

    lateinit var list: Array<MutableList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

        var isNightModeOn = appPrefs.getBoolean("isNightModeOn", false)

        if (isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        settings.setOnClickListener {
            isNightModeOn = !isNightModeOn
            if (isNightModeOn){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            prefsEditor.putBoolean("isNightModeOn", isNightModeOn)
            prefsEditor.apply()
        }

        easyLevelIcon.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            prefsEditor.putBoolean("gameFlag",true)
            prefsEditor.putInt("book", 0)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        middleLevelIcon.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            prefsEditor.putBoolean("gameFlag",true)
            prefsEditor.putInt("book", 1)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        hardLevelIcon.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            prefsEditor.putBoolean("gameFlag",true)
            prefsEditor.putInt("book", 2)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        easyLevelText.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            prefsEditor.putBoolean("gameFlag",true)
            prefsEditor.putInt("book", 0)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        middleLevelText.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            prefsEditor.putBoolean("gameFlag",true)
            prefsEditor.putInt("book", 1)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        hardLevelText.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            prefsEditor.putBoolean("gameFlag",true)
            prefsEditor.putInt("book", 2)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}