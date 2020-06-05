package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_teams.*

class Teams : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        var teams: MutableList<String> = listOf("1 team", "2 team", "3 team") as MutableList<String>

        val teamsAdapter = TeamsAdapter(this, teams)
        teamsView.adapter = teamsAdapter
        teamsView.layoutManager = LinearLayoutManager(this)

        continueButton.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
            teams = teamsAdapter.getter().toMutableList()
            intent.putExtra("teams", teams.toTypedArray())
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        backButton.setOnClickListener {
            finish()
        }

        addTeamButton.setOnClickListener {
            teams.add("New team")
            val teamsAdapter = TeamsAdapter(this, teams)
            teamsView.adapter = teamsAdapter
            teamsView.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}