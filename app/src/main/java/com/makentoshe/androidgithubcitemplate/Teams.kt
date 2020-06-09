package com.makentoshe.androidgithubcitemplate

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_teams.*

class Teams : AppCompatActivity() {

    lateinit var teamsAdapter: TeamsAdapter
    lateinit var teams: MutableList<String>
    var currentPosition: Int = 0
    lateinit var addTeamDialog: Dialog
    lateinit var closeAddTeamDialog: Button
    lateinit var tnld: TextInputLayout
    lateinit var tnet: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        addTeamDialog = Dialog(this)

        teams = mutableListOf()
        currentPosition = 0
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
            showAddTeamDialog()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    fun addTeam(name: String, position: Int) {
        teams.add(name)
        teamsAdapter.notifyItemInserted(position)
    }

    fun deleteTeam(position: Int) {
        teams.removeAt(position)
        teamsAdapter.notifyItemRemoved(position)
        teamsAdapter.currentMinus()

        if (currentPosition >= 2) {
            continueButtonTeams.isClickable = true
            continueButtonTeams.setTextColor(resources.getColor(R.color.activeButton))
        } else {
            continueButtonTeams.isClickable = false
            continueButtonTeams.setTextColor(resources.getColor(R.color.noActiveButton))
        }
    }

    fun createRecyclerView() {
        teamsAdapter = TeamsAdapter(this, teams, currentPosition)
        teamsView.adapter = teamsAdapter
        teamsView.layoutManager = LinearLayoutManager(this)

        teamsAdapter.setOnItemClickListener(object : TeamsAdapter.onItemClickListener {
            override fun onDeleteClicked(position: Int) {
                currentPosition--
                deleteTeam(position)
                teamsAdapter.currentMinus()
            }

            override fun onEditClicked(position: Int) {

                addTeamDialog.setContentView(R.layout.diaolog_team_name)
                closeAddTeamDialog = addTeamDialog.findViewById(R.id.addTeamDialog)
                tnld = addTeamDialog.findViewById(R.id.teamNameLayoutDialog)
                tnet = addTeamDialog.findViewById(R.id.teamNameDialog)
                addTeamDialog.show()
                tnld.editText?.setText(teams[position])
                closeAddTeamDialog.setOnClickListener {
                    addTeamDialog.cancel()
                    teams[position] = tnld.editText?.text.toString()
                    teamsAdapter.notifyItemChanged(position)
                }
                tnet.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(p0: Editable?) {}

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        if (tnld.editText?.text.toString()
                                .isEmpty() || tnld.editText?.text.toString()
                                .isBlank() || tnld.editText?.text.toString().length > 20
                        ) {
                            tnld.error = "Ошибка"
                            closeAddTeamDialog.isClickable = false
                            closeAddTeamDialog.background =
                                resources.getDrawable(R.drawable.add_team_no_active)
                        } else {
                            closeAddTeamDialog.isClickable = true
                            closeAddTeamDialog.background =
                                resources.getDrawable(R.drawable.new_round_button)
                        }
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        if (tnld.editText?.text.toString()
                                .isEmpty() || tnld.editText?.text.toString()
                                .isBlank() || tnld.editText?.text.toString().length > 20
                        ) {
                            tnld.error = "Ошибка"
                            closeAddTeamDialog.isClickable = false
                            closeAddTeamDialog.background =
                                resources.getDrawable(R.drawable.add_team_no_active)
                        } else {
                            closeAddTeamDialog.isClickable = true
                            closeAddTeamDialog.background =
                                resources.getDrawable(R.drawable.new_round_button)
                            tnld.isErrorEnabled = false
                            tnld.isErrorEnabled = true
                        }
                    }

                })
            }
        })
    }

    fun showAddTeamDialog() {
        addTeamDialog.setContentView(R.layout.diaolog_team_name)
        closeAddTeamDialog = addTeamDialog.findViewById(R.id.addTeamDialog)
        tnld = addTeamDialog.findViewById(R.id.teamNameLayoutDialog)
        tnet = addTeamDialog.findViewById(R.id.teamNameDialog)
        addTeamDialog.show()
        closeAddTeamDialog.setOnClickListener {
            addTeamDialog.cancel()
            addTeam(tnld.editText?.text.toString(), currentPosition++)

            if (currentPosition >= 2) {
                continueButtonTeams.isClickable = true
                continueButtonTeams.setTextColor(resources.getColor(R.color.activeButton))
            } else {
                continueButtonTeams.isClickable = false
                continueButtonTeams.setTextColor(resources.getColor(R.color.noActiveButton))
            }
        }

        if (tnld.editText?.text.toString().isEmpty() || tnld.editText?.text.toString()
                .isBlank() || tnld.editText?.text.toString().length > 25
        ) {
            tnld.error = "Ошибка"
            closeAddTeamDialog.isClickable = false
            closeAddTeamDialog.background = resources.getDrawable(R.drawable.add_team_no_active)
        } else {
            closeAddTeamDialog.isClickable = true
            closeAddTeamDialog.background = resources.getDrawable(R.drawable.new_round_button)
        }

        tnet.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (tnld.editText?.text.toString().isEmpty() || tnld.editText?.text.toString()
                        .isBlank() || tnld.editText?.text.toString().length > 20
                ) {
                    tnld.error = "Ошибка"
                    closeAddTeamDialog.isClickable = false
                    closeAddTeamDialog.background =
                        resources.getDrawable(R.drawable.add_team_no_active)
                } else {
                    closeAddTeamDialog.isClickable = true
                    closeAddTeamDialog.background =
                        resources.getDrawable(R.drawable.new_round_button)
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (tnld.editText?.text.toString().isEmpty() || tnld.editText?.text.toString()
                        .isBlank() || tnld.editText?.text.toString().length > 20
                ) {
                    tnld.error = "Ошибка"
                    closeAddTeamDialog.isClickable = false
                    closeAddTeamDialog.background =
                        resources.getDrawable(R.drawable.add_team_no_active)
                } else {
                    closeAddTeamDialog.isClickable = true
                    closeAddTeamDialog.background =
                        resources.getDrawable(R.drawable.new_round_button)
                    tnld.isErrorEnabled = false
                    tnld.isErrorEnabled = true
                }
            }

        })
    }
}