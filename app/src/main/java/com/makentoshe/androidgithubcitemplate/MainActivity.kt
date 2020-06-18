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

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()
        var teamsAmount = appPrefs.getInt("teamsAmount", 0)
        var teams = MutableList(teamsAmount){""}
        for (i in 0 until teamsAmount)
            teams[i] = appPrefs.getString("team$i", "").toString()
        var wordsForWin: Int
        var roundLength: Int
        var fineChanger: Boolean
        var generalLast: Boolean
        var tasks: Boolean
        var robbery: Boolean
        var book: Int
        var teamsScores = Array(teamsAmount){0}
        for (i in 0 until teamsAmount)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)
        var currentTeamText: String
        var currentRoundText: String
        var counter: Int
        var roundNumber = appPrefs.getInt("roundNumber", 0)
        val list: Array<MutableList<String>> = Array(teamsAmount) { MutableList(roundNumber) { "0.0" } }
        var teamsFlag = appPrefs.getBoolean("teamsFlag", false)
        var gameSettingsFlag = appPrefs.getBoolean("gameSettingsFlag", false)
        var levelsFlag = appPrefs.getBoolean("levelsFlag", false)
        var gameFlag = appPrefs.getBoolean("gameFlag", false)

        if (!teamsFlag && !gameFlag && !levelsFlag && !gameFlag){
            continueGameButton.isClickable = false
            continueGameButton.background = resources.getDrawable(R.drawable.add_team_no_active)
            continueGameButton.setTextColor(resources.getColorStateList(R.color.white))
        }

        continueGameButton.setOnClickListener {
            if (gameFlag){
                val intent = Intent(this, Game::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            } else {
                if (levelsFlag) {
                    val intent = Intent(this, Levels::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                } else {
                    if (gameSettingsFlag) {
                        val intent = Intent(this, GameSettings::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    } else {
                        if (teamsFlag){
                            val intent = Intent(this, Teams::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                        } else {
                            continueGameButton.isClickable = false
                            continueGameButton.background = resources.getDrawable(R.drawable.add_team_no_active)
                            continueGameButton.setTextColor(resources.getColorStateList(R.color.white))
                        }
                    }
                }
            }
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
            prefsEditor.putBoolean("teamsFlag", true)
            prefsEditor.putBoolean("gameSettingsFlag", false)
            prefsEditor.putBoolean("levelsFlag", false)
            prefsEditor.putBoolean("gameFlag", false)
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
