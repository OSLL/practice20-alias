package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {

    lateinit var list: Array<MutableList<String>>
    lateinit var teams: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val appPrefs: SharedPreferences = getSharedPreferences("AppNightMode", 0)
        var isNightModeOn = appPrefs.getBoolean("NightMode", false)
        var prefsEditor: SharedPreferences.Editor = appPrefs.edit()

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

        val teamsAmount  = appPrefs.getInt("teamsAmount", 0)
        teams = Array(teamsAmount){"Название команды"}
        for (i in 0 until teamsAmount)
            list[i] = this.intent.getStringArrayExtra("list$i")!!.toMutableList()

        for (i in 0 until teamsAmount)
            teams[i] = appPrefs.getString("team$i", "Название").toString()

        var list: Array<MutableList<String>> = Array(teams.size){ MutableList(0){"0.0"} }
        for (i in teams.indices)
            list[i] = appPrefs.getStringSet("list$i", setOf())!!.toMutableList()
        val wordsForWin = appPrefs.getString("wordsForWin", "10")
        val fineChecker = appPrefs.getBoolean("fineChecker", false)
        val robbery = appPrefs.getBoolean("robbery", false)
        var currentTeamText: String = this.intent.getStringExtra("newTeam")!!
        val currentRoundText: String = this.intent.getStringExtra("newRound")!!
        var counter = this.intent.getIntExtra("counter", -1)
        var teamsScores = IntArray(teams.size){0}
        var m: Array<Float>

        list = Array(teams.size) { MutableList(0) { "0.0" } }

        currentTeamText = teams[counter]
        currentTeam.text = currentTeamText
        currentRound.text = currentRoundText

        val teamsAdapter = TeamsAdapterGame(this, teams, teamsScores)
        teamsViewGame.adapter = teamsAdapter
        teamsViewGame.layoutManager = LinearLayoutManager(this)

        if ((wordsForWin!!.toInt() - (wordsForWin!!.toInt() % 10)) / 10 == 1) {
            pointsText.text = wordsForWin + " очков"
        } else {
            when (wordsForWin.toInt() % 10) {
                0 -> pointsText.text = wordsForWin + " очков"
                1 -> pointsText.text = wordsForWin + " очко"
                2, 3, 4 -> pointsText.text = wordsForWin + " очка"
                5, 6, 7, 8, 9 -> pointsText.text = wordsForWin + " очков"
            }
        }

        /* if (settingsInfo[1]) startRound.setText(settingsText[0].toString()) else startRound.setText(settingsText[1].toString()) *///Для показа работоспособности

        startRound.setOnClickListener {
            if (teamsScores[counter] % 5 == 0 && robbery && teamsScores[counter] > 0) {
                val intent = Intent(this, RobberyRound::class.java)
                intent.putExtra("teamsAmount", teams.size)
                intent.putExtra("round", currentRound.text.toString())
                intent.putExtra("counter", counter)
                intent.putExtra("currentTeam", currentTeamText)
                intent.putExtra("currentRound", currentRoundText)
                intent.putExtra("teamsScores", teamsScores)
                intent.putExtra("book", this.intent.getIntExtra("book", -1))
                for (i in teams.indices)
                    intent.putExtra("list$i", list[i].toTypedArray())
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
            } else {
                val intent = Intent(this, Round::class.java)
                intent.putExtra("teamsAmount", teams.size)
                intent.putExtra("round", currentRound.text.toString())
                intent.putExtra("counter", counter)
                intent.putExtra("currentTeam", currentTeamText)
                intent.putExtra("currentRound", currentRoundText)
                intent.putExtra("teamsScores", teamsScores)
                intent.putExtra("book", this.intent.getIntExtra("book", -1))
                for (i in teams.indices)
                    intent.putExtra("list$i", list[i].toTypedArray())
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
            }
        }
    }

    override fun finish() {
        super.finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}