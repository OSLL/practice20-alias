package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {

    lateinit var list: Array<MutableList<String>>
    lateinit var teams: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()
        val teamsAmount = appPrefs.getInt("teamsAmount", 0)
        teams = Array(teamsAmount){""}
        for (i in 0 until teamsAmount)
            teams[i] = appPrefs.getString("team$i", "").toString()
        var currentTeamText = appPrefs.getString("currentTeamText", "1 команда").toString()
        val currentRoundText: String = appPrefs.getString("currentRoundText", "1 раунд").toString()
        val counter = appPrefs.getInt("counter", -1)
        val teamsScores = Array(teamsAmount){0}
        for (i in 0 until teamsAmount)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)
        val wordsForWin = appPrefs.getInt("wordsForWin", 10)
        val robbery = appPrefs.getBoolean("robbery", false)

        currentTeamText = teams[counter]
        prefsEditor.putString("currentTeamText", currentTeamText)
        prefsEditor.apply()

        currentTeam.text = currentTeamText
        currentRound.text = currentRoundText

        val teamsAdapter = TeamsAdapterGame(this, teams, teamsScores)
        teamsViewGame.adapter = teamsAdapter
        teamsViewGame.layoutManager = LinearLayoutManager(this)

        if ((wordsForWin - (wordsForWin % 10)) / 10 == 1) {
            pointsText.text = wordsForWin.toString() + " очков"
        } else {
            when (wordsForWin % 10) {
                0 -> pointsText.text = wordsForWin.toString() + " очков"
                1 -> pointsText.text = wordsForWin.toString() + " очко"
                2, 3, 4 -> pointsText.text = wordsForWin.toString() + " очка"
                5, 6, 7, 8, 9 -> pointsText.text = wordsForWin.toString() + " очков"
            }
        }

        startRound.setOnClickListener {
            if (teamsScores[counter] % 5 == 0 && robbery && teamsScores[counter] > 0) {
                val intent = Intent(this, RobberyRound::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
            } else {
                val intent = Intent(this, Round::class.java)
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