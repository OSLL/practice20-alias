package com.threemonkeys.alias

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {

    lateinit var list: Array<MutableList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

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

        val teamsAmount = appPrefs.getInt("teamsAmount", 0)
        val teams=Array(teamsAmount){""}
        for (i in 0 until teamsAmount)
            teams[i] = appPrefs.getString("team$i", "").toString()
        val roundNumber = appPrefs.getInt("roundNumber", 0)

        list = Array(teams.size) { MutableList(roundNumber) { "0.0" } }
        for (i in teams.indices)
            for (j in 0 until roundNumber)
                list[i][j] = appPrefs.getString("list[$i][$j]", "0.0").toString()

        prefsEditor.putBoolean("teamsFlag", false)
        prefsEditor.putBoolean("gameSettingsFlag", false)
        prefsEditor.putBoolean("levelsFlag", false)
        prefsEditor.putBoolean("gameFlag", false)
        prefsEditor.apply()

        backButton.setOnClickListener {
            finish()
        }

        toMenuButton.setOnClickListener {
            finish()
        }

        val firstAdapter = FirstAdapter(this, list, teams.toMutableList())
        firstView.adapter = firstAdapter
        firstView.layoutManager = LinearLayoutManager(this)
    }

    override fun finish() {
        super.finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}