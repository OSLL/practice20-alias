package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_win_page.*

class WinPage : AppCompatActivity() {

    lateinit var list: Array<MutableList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win_page)

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()
        val teamsAmount = appPrefs.getInt("teamsAmount", 0)

        val teams = Array(teamsAmount) { "" }
        val teamsScores = Array(teamsAmount) {0}
        for (i in teams.indices)
            teams[i] = appPrefs.getString("team$i", "").toString()
        for (i in teams.indices)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)

        val winner: String = appPrefs.getString("winner", "Error team")!!
        val max: Int = appPrefs.getInt("max", -1)!!
        if ((max - (max % 10)) / 10 == 1) {
            youWin.text = "Команда: $winner"
            youWin1.text = "Победила!"
            youWin2.text = "Набрав $max очков!"
        } else {
            when (max % 10) {
                1 -> {
                    youWin.text = "Команда: $winner"
                    youWin1.text = "Победила!"
                    youWin2.text = "Набрав $max очко!!!"
                }
                2, 3, 4 -> {
                    youWin.text = "Команда: $winner"
                    youWin1.text = "Победила!"
                    youWin2.text = "Набрав $max очка!"
                }
                0, 5, 6, 7, 8, 9 -> {
                    youWin.text = "Команда: $winner "
                    youWin1.text = "Победила!"
                    youWin2.text = "Набрав $max очков!"
                }
            }
        }

        backToMenu.setOnClickListener {
            prefsEditor.putBoolean("teamsFlag", false)
            prefsEditor.putBoolean("gameSettingsFlag", false)
            prefsEditor.putBoolean("levelsFlag", false)
            prefsEditor.putBoolean("gameFlag", false)
            prefsEditor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        toStatistics.setOnClickListener {
            val intent = Intent(this, Statistics::class.java)
            startActivity(intent)
            finish()
        }

        val backgroundAnimation: Animation = RotateAnimation(
            0.0f,
            360.0f,
            RotateAnimation.RELATIVE_TO_SELF,
            .5f,
            RotateAnimation.RELATIVE_TO_SELF,
            .5f)
        backgroundAnimation.duration = 10000
        backgroundAnimation.repeatMode = Animation.INFINITE
        backgroundAnimation.repeatCount = Animation.INFINITE
        backgroundAnimation.interpolator = LinearInterpolator()
        winPageBackground.startAnimation(backgroundAnimation)
    }
}