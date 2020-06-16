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

        val appPrefs: SharedPreferences = getSharedPreferences("AppNightMode", 0)
        var isNightModeOn = appPrefs.getBoolean("NightMode", false)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()
        val teamsAmount = appPrefs.getInt("teamsAmount", 0)

        list = Array(teamsAmount) { MutableList(0) { "0.0" } }


        if (isNightModeOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        settings.setOnClickListener {
            isNightModeOn = !isNightModeOn
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                prefsEditor.putBoolean("NightMode", isNightModeOn)
                prefsEditor.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                prefsEditor.putBoolean("NightMode", isNightModeOn)
                prefsEditor.apply()
            }
        }

        val intentTeam: String = "1 команда"
        val intentRound: String = "1 раунд"
        val additional: Int = 0

        easyLevelIcon.setOnClickListener {
            val intent = Intent(this, Game::class.java)
            prefsEditor.putInt("book", 0)
            prefsEditor.apply()
            intent.putExtra("newTeam", intentTeam)
            intent.putExtra("newRound", intentRound)
            intent.putExtra("counter", additional)
            for (i in list.indices)
                intent.putExtra("list$i", list[i].toTypedArray())
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        middleLevelIcon.setOnClickListener {

            val intent = Intent(this, Game::class.java)
            prefsEditor.putInt("book", 1)
            prefsEditor.apply()
            intent.putExtra("newTeam", intentTeam)
            intent.putExtra("newRound", intentRound)
            intent.putExtra("counter", additional)
            for (i in list.indices)
                intent.putExtra("list$i", list[i].toTypedArray())
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        hardLevelIcon.setOnClickListener {

            val intent = Intent(this, Game::class.java)
            prefsEditor.putInt("book", 2)
            prefsEditor.apply()
            intent.putExtra("newTeam", intentTeam)
            intent.putExtra("newRound", intentRound)
            intent.putExtra("counter", additional)
            for (i in list.indices)
                intent.putExtra("list$i", list[i].toTypedArray())
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        easyLevelText.setOnClickListener {

            val intent = Intent(this, Game::class.java)
            prefsEditor.putInt("book", 0)
            prefsEditor.apply()
            intent.putExtra("newTeam", intentTeam)
            intent.putExtra("newRound", intentRound)
            intent.putExtra("counter", additional)
            for (i in list.indices)
                intent.putExtra("list$i", list[i].toTypedArray())
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        middleLevelText.setOnClickListener {

            val intent = Intent(this, Game::class.java)
            prefsEditor.putInt("book", 1)
            prefsEditor.apply()
            intent.putExtra("newTeam", intentTeam)
            intent.putExtra("newRound", intentRound)
            intent.putExtra("counter", additional)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        hardLevelText.setOnClickListener {

            val intent = Intent(this, Game::class.java)
            prefsEditor.putInt("book", 2)
            prefsEditor.apply()
            intent.putExtra("newTeam", intentTeam)
            intent.putExtra("newRound", intentRound)
            intent.putExtra("counter", additional)
            for (i in list.indices)
                intent.putExtra("list$i", list[i].toTypedArray())
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