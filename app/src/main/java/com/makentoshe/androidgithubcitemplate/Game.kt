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

        var currentTeamText: String = this.intent.getStringExtra("newTeam")
        var currentRoundText: String = this.intent.getStringExtra("newRound")
        var counter = this.intent.getIntExtra("counter", 0)

        currentTeam.setText(currentTeamText)
        currentRound.setText(currentRoundText)

        val teamsAdapter = TeamsAdapterGame(this, teams)
        teamsViewGame.adapter = teamsAdapter
        teamsViewGame.layoutManager = LinearLayoutManager(this)

        if ((settingsText[0] - (settingsText[0] % 10)) / 10 == 1){
            pointsText.setText(settingsText[0].toString() + " очков")
        } else {
            when (settingsText[0] % 10){
                0 -> pointsText.setText(settingsText[0].toString() + " очков")
                1 -> pointsText.setText(settingsText[0].toString() + " очко")
                2, 3, 4 -> pointsText.setText(settingsText[0].toString() + " очка")
                5, 6, 7, 8, 9 -> pointsText.setText(settingsText[0].toString() + " очков")
            }
        }

       /* if (settingsInfo[1]) startRound.setText(settingsText[0].toString()) else startRound.setText(settingsText[1].toString()) *///Для показа работоспособности

        startRound.setOnClickListener {
            val intent = Intent(this, Round::class.java)
            intent.putExtra("settingsText", settingsText)
            intent.putExtra("teamsAmount", teams.size)
            intent.putExtra("round", currentRound.text.toString())
            intent.putExtra("settingsInfo", settingsInfo)
            intent.putExtra("teams", teams)
            intent.putExtra("counter", counter)
            intent.putExtra("currentTeam", currentTeamText)
            intent.putExtra("currentRound", currentRoundText)
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