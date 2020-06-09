package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_round.*

class Round : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round)

        var settingsText:IntArray = this.intent.getIntArrayExtra("settingsText")
        var settingsInfo:BooleanArray = this.intent.getBooleanArrayExtra("settingsInfo")
        var teamNums: Int = this.intent.getIntExtra("teamsAmount", 2)
        var newRound: String = this.intent.getStringExtra("round")
        var teamsExtra = this.intent.getStringArrayExtra("teams")

        roundTitle.text = this.intent.getStringExtra("currentTeam")
        roundText.text = this.intent.getStringExtra("currentRound")

        val teamsNums = Array<Int>(teamNums){it+1}
        var count = this.intent.getIntExtra("counter", 0)

        timerCounter.setText(settingsText[1].toString())
        chronometer.setBase((timerCounter.text.toString().toInt() * 1000).toLong())

        backButton.setOnClickListener {
            finish()
        }

        cardImage.setOnClickListener {
            chronometer.start()
            cardImage.isClickable = false
            chronometer.setOnChronometerTickListener {
                var elapsedMillis: Long = SystemClock.elapsedRealtime() - chronometer.base
                if (elapsedMillis > 1000) {
                    timerCounter.text = (timerCounter.text.toString().toInt() - 1).toString()
                    if (timerCounter.text.toString().toInt() == 0){
                        chronometer.stop()
                        Toast.makeText(applicationContext, "Время вышло!", Toast.LENGTH_SHORT).show()
                        count += 1
                        Log.d("TAG", "$count")
                        if(count == teamNums) {
                            count = 0
                            newRound = (newRound.substringBefore(" ").toInt() + 1).toString() + " раунд"
                        }
                        var newTeam: String = teamsNums[count].toString() + " команда"

                        val intent = Intent(this, Game::class.java)
                        intent.putExtra("newRound", newRound)
                        intent.putExtra("newTeam", newTeam)
                        intent.putExtra("settingsText", settingsText)
                        intent.putExtra("settingsInfo", settingsInfo)
                        intent.putExtra("teams", teamsExtra)
                        intent.putExtra("counter", count)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                        finish()
                    }

                    elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
                }
            }
        }

        check.setOnClickListener{
            knowWordsCounter.text = (knowWordsCounter.text.toString().toInt() + 1).toString()
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