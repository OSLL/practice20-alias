package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

            for (i in teamsAdapter.textsGetter()) {
                if (!i.editText?.text.toString().isEmpty() && !i.editText?.text.toString().isBlank() && !(i.editText?.text.toString().length>25)) {
                    continueButtonTeams.isClickable=true
                    continueButtonTeams.setTextColor(resources.getColor(R.color.activeButton))
                }

            }

        addTeamButton.setOnClickListener {
            if (currentPosition<7) addTeam(currentPosition++)
            else Toast.makeText(this, "You don't have so many friends", Toast.LENGTH_LONG).show()
        }

        deleteTeamButton.setOnClickListener {
            if (currentPosition>2) deleteTeam(--currentPosition)
            else Toast.makeText(this, "You are unsociable", Toast.LENGTH_LONG).show()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun addTeam(position: Int) {
        teams.add(position, "New team")
        teamsAdapter.notifyItemInserted(position)
        teamsAdapter.adderFunction()
        if (currentPosition <= 2) {
            for (i in teamsAdapter.buttonsGetter()) {
                i.background = resources.getDrawable(R.drawable.delete_button_no_active)
                i.isClickable = false
            }
        } else {
            for (i in teamsAdapter.buttonsGetter()) {
                i.background = resources.getDrawable(R.drawable.delete_button)
                i.isClickable = true
            }
        }
    }

    fun deleteTeam(position: Int){
        teams.removeAt(position)
        teamsAdapter.notifyItemRemoved(position)
        teamsAdapter.texts.removeAt(position)
        teamsAdapter.currentMinus()
        if (currentPosition <= 2) {
            for (i in teamsAdapter.buttonsGetter()) {
                i.background = resources.getDrawable(R.drawable.delete_button_no_active)
                i.isClickable = false
            }
        } else {
            for (i in teamsAdapter.buttonsGetter()) {
                i.background = resources.getDrawable(R.drawable.delete_button)
                i.isClickable = true
            }
        }
    }

    fun createRecyclerView() {
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

        teamsAdapter.setOnItemClickListener(object : TeamsAdapter.onItemClickListener{
            override fun onDeleteClicked(position: Int) {
                currentPosition--
                deleteTeam(position)
                teamsAdapter.currentMinus()
            }
        })
    }
}