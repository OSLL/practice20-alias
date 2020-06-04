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

        var teams: Array<String> = arrayOf("1 команда", "2 комнада")

        val teamsAdapter = TeamsAdapter(this, teams)
        teamsView.adapter = teamsAdapter
        teamsView.layoutManager = LinearLayoutManager(this)

        continueButton.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
            intent.putExtra("teams", teams)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        backButton.setOnClickListener {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}