package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()
        var teamsAmount = appPrefs.getInt("teamsAmount", 0)
        var teams = MutableList(teamsAmount){""}
        for (i in 0 until teamsAmount)
            teams[i] = appPrefs.getString("team$i", "").toString()
        var wordsForWin = appPrefs.getInt("wordsForWin", 10)
        var roundLength = appPrefs.getInt("roundLength", 10)
        var fineChanger = appPrefs.getBoolean("fineChanger", false)
        var generalLast = appPrefs.getBoolean("generalLast", false)
        var tasks = appPrefs.getBoolean("tasks", false)
        var robbery = appPrefs.getBoolean("robbery", false)
        var book = appPrefs.getInt("book", -1)
        var teamsScores = Array(teamsAmount){0}
        for (i in 0 until teamsAmount)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)
        var currentTeamText = appPrefs.getString("currentTeamText", "1 команда")
        var currentRoundText = appPrefs.getString("currentRoundText", "1 раунд")
        var counter = appPrefs.getInt("counter", 0)
        var roundNumber = appPrefs.getInt("roundNumber", 0)
        val list: Array<MutableList<String>> = Array(teamsAmount) { MutableList(roundNumber) { "0.0" } }

        continueGameButton.setOnClickListener {
            var intent = Intent(this, Game::class.java)
            startActivity(intent)
        }

        newGameButton.setOnClickListener {
            val intent = Intent(this, Teams::class.java)
            roundNumber = 0
            teamsAmount = 0
            teams = MutableList(teamsAmount){""}
            wordsForWin = 10
            roundLength = 10
            fineChanger = false
            generalLast = false
            tasks = false
            robbery = false
            book = -1
            counter = 0
            teamsScores = Array(teamsAmount){0}
            currentTeamText = "1 команда"
            currentRoundText = "1 раунд"
            for (i in 0 until teamsAmount)
                for (j in 0 until roundNumber)
                    list[i][j] = "0.0"
            prefsEditor.putInt("teamsAmount", teamsAmount)
            for (i in 0 until teamsAmount){
                prefsEditor.putString("team$i", teams[i])
            }

            for (i in 0 until teamsAmount) {
                prefsEditor.putInt("teamsScores$i", teamsScores[i])
            }
            for (i in 0 until teamsAmount)
                for (j in 0 until roundNumber)
                    prefsEditor.putString("list[$i][$j]", list[i][j])
            prefsEditor.putInt("wordsForWin", wordsForWin)
            prefsEditor.putInt("roundLength", roundLength)
            prefsEditor.putBoolean("fineChanger", fineChanger)
            prefsEditor.putBoolean("generalLast", generalLast)
            prefsEditor.putBoolean("tasks", tasks)
            prefsEditor.putBoolean("robbery", robbery)
            prefsEditor.putInt("book", book)
            prefsEditor.putString("currentRoundText", currentRoundText)
            prefsEditor.putString("currentTeamText", currentTeamText)
            prefsEditor.putInt("counter", counter)
            prefsEditor.putInt("roundNumber",roundNumber)
            prefsEditor.clear()
            prefsEditor.apply()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        rules_button.setOnClickListener {
            val intent = Intent(this, Rules::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
        }
    }
}
