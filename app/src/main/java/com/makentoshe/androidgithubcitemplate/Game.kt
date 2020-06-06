package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        var teams = this.intent.getStringArrayExtra("teams")
        var settingsText:IntArray = this.intent.getIntArrayExtra("settingsText")
        var settingsInfo:BooleanArray = this.intent.getBooleanArrayExtra("settingsInfo")

        val teamsAdapter = TeamsAdapterGame(this, teams)
        teamsViewGame.adapter = teamsAdapter
        teamsViewGame.layoutManager = LinearLayoutManager(this)

        pointsText.setText(settingsText[0].toString())

       /* if (settingsInfo[1]) startRound.setText(settingsText[0].toString()) else startRound.setText(settingsText[1].toString()) *///Для показа работоспособности

        startRound.setOnClickListener {
            val intent = Intent(this, Round::class.java)
            intent.putExtra("settingsText", settingsText)
            intent.putExtra("settingsInfo", settingsInfo)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
        }
    }

    override fun finish() {
        super.finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}