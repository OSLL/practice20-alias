package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_teams.*

class Teams : AppCompatActivity() {

    lateinit var teamsAdapter: TeamsAdapter
    lateinit var teams: MutableList<String>
    var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        teams = mutableListOf("1 team", "2 team")
        currentPosition = 2
        createRecyclerView()

        continueButtonTeams.setOnClickListener {
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
            addTeam(currentPosition++)
            if (currentPosition>=7) addTeamButton.isClickable=false
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun addTeam(position: Int){
        teams.add(position, "New team")
        teamsAdapter.notifyItemInserted(position)
        teamsAdapter.afjsf()

    }

    fun createRecyclerView(){
        teamsAdapter = TeamsAdapter(
            this,
            teams,
            continueButtonTeams,
            resources.getColorStateList(R.color.noActiveButton),
            resources.getColorStateList(R.color.activeButton),
            resources.getDrawable(R.drawable.delete_button),
            resources.getDrawable(R.drawable.delete_button_no_active),
            currentPosition
        )
        teamsView.adapter = teamsAdapter
        teamsView.layoutManager = LinearLayoutManager(this)
    }
}