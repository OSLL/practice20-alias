package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {

    lateinit var list: Array<MutableList<String>>
    lateinit var teams: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

        val teamsAmount = appPrefs.getInt("teamsAmount", 0)
        teams = Array(teamsAmount){""}
        for (i in 0 until teamsAmount)
            teams[i] = appPrefs.getString("team$i", "").toString()
        var currentTeamText: String = appPrefs.getString("currentTeamText", "1 команда").toString()
        var currentRoundText: String = appPrefs.getString("currentRoundText", "1 раунд").toString()
        var counter = appPrefs.getInt("counter", -1)
        var teamsScores = Array(teamsAmount){0}
        for (i in 0 until teamsAmount)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)
        val wordsForWin = appPrefs.getInt("wordsForWin", 10)
        val robbery = appPrefs.getBoolean("robbery", false)

//        list = Array(teams.size) { MutableList(0) { "0.0" } }
//        for (i in teams.indices)
//            list[i] = this.intent.getStringArrayExtra("list$i").toMutableList()

        currentTeamText = teams[counter]
        prefsEditor.putString("currentTeamText", currentTeamText)
        prefsEditor.apply()
        currentTeam.text = currentTeamText
        Log.e("Round", currentRoundText)
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

        /* if (settingsInfo[1]) startRound.setText(wordsForWin.toString()) else startRound.setText(settingsText[1].toString()) *///Для показа работоспособности

        startRound.setOnClickListener {
            if (teamsScores[counter] % 5 == 0 && robbery && teamsScores[counter] > 0) {
                val intent = Intent(this, RobberyRound::class.java)
//                intent.putExtra("teamsAmount", teams.size)
//                intent.putExtra("round", currentRound.text.toString())
//                intent.putExtra("teams", teams)
//                intent.putExtra("counter", counter)
//                intent.putExtra("currentTeam", currentTeamText)
//                intent.putExtra("currentRound", currentRoundText)
//                intent.putExtra("teamsScores", teamsScores)
//                intent.putExtra("book", this.intent.getIntExtra("book", -1))
//                for (i in teams.indices)
//                    intent.putExtra("list$i", list[i].toTypedArray())
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
            } else {
                val intent = Intent(this, Round::class.java)
//                intent.putExtra("teamsAmount", teams.size)
//                intent.putExtra("round", currentRound.text.toString())
//                intent.putExtra("teams", teams)
//                intent.putExtra("counter", counter)
//                intent.putExtra("currentTeam", currentTeamText)
//                intent.putExtra("currentRound", currentRoundText)
//                intent.putExtra("teamsScores", teamsScores)
//                intent.putExtra("book", this.intent.getIntExtra("book", -1))
//                for (i in teams.indices)
//                    intent.putExtra("list$i", list[i].toTypedArray())
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