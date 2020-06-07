package com.makentoshe.androidgithubcitemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_round.*
import android.widget.Toast
import android.widget.Chronometer
import android.os.SystemClock
import android.util.Log

class Round : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round)

        var settingsText:IntArray = this.intent.getIntArrayExtra("settingsText")
        var settingsInfo:BooleanArray = this.intent.getBooleanArrayExtra("settingsInfo")

        timerCounter.setText(settingsText[1].toString())
        chronometer.setBase((timerCounter.text.toString().toInt() * 1000).toLong())

        backButton.setOnClickListener {
            finish()
        }

        cardImage.setOnClickListener {
            chronometer.start()
            cardImage.setClickable(false)
            chronometer.setOnChronometerTickListener {
                var elapsedMillis: Long = SystemClock.elapsedRealtime() - chronometer.base
                if (elapsedMillis > 1000) {
                    timerCounter.setText((timerCounter.text.toString().toInt() - 1).toString())
                    if (timerCounter.text.toString().toInt() == 0){
                        chronometer.stop()
                    }
                    elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
                }
            }
        }

        check.setOnClickListener{
            knowWordsCounter.setText((knowWordsCounter.text.toString().toInt() + 1).toString())
        }

        cross.setOnClickListener{
            skipWordsCounter.setText((skipWordsCounter.text.toString().toInt() + 1).toString())
        }
    }



    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
    }
}