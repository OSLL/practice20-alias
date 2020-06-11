package com.makentoshe.androidgithubcitemplate

import android.content.Intent
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

        var teamsExtra = this.intent.getStringArrayExtra("teams")!!

        list = Array(teamsExtra.size) { MutableList(0) { "0.0" } }
        for (i in teamsExtra.indices)
            list[i] = this.intent.getStringArrayExtra("list$i")!!.toMutableList()

        var winner: String = this.intent.getStringExtra("WinTeamName")!!
        var max: Int = this.intent.getIntExtra("WinTeamScore", -1)
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        toStatistics.setOnClickListener {
            val intent = Intent(this, Statistics::class.java)
            for (i in teamsExtra.indices)
                intent.putExtra("list$i", list[i].toTypedArray())
            intent.putExtra("teams", teamsExtra)
            startActivity(intent)
            finish()
        }

        var backgroundAnimation: Animation = RotateAnimation(
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