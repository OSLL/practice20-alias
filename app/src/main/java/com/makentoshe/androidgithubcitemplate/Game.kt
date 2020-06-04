package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_game.*

class Game : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        Log.d("Sas","Status")
        var teams = this.intent.getStringArrayExtra("teams")
        Log.d("Sas","${teams[0].toString()}")

        val teamsAdapter = TeamsAdapter(this, teams)
        teamsView1.adapter = teamsAdapter
        teamsView1.layoutManager = LinearLayoutManager(this)

        startRound.setOnClickListener {
            val intent = Intent(this, Round::class.java)
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