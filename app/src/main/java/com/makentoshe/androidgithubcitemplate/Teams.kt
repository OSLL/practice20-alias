package com.makentoshe.androidgithubcitemplate

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
    lateinit var list: Array<MutableList<String>>
    var teamsAmount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams)

        var appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

        addTeamDialog = Dialog(this)

        teamsAmount = appPrefs.getInt("teamsAmount", 0)
        Log.e("teamsAmount", "${teamsAmount}")

        teams = MutableList(teamsAmount){""}

        for (i in 0 until teamsAmount)
            teams[i] = appPrefs.getString("team$i", "").toString()
        currentPosition = teams.size

//        list = Array(teams.size) { MutableList(0) { "0.0" } }
//        for (i in teams.indices)
//            list[i] = this.intent.getStringArrayExtra("list$i").toMutableList()
//        Log.e("Sas", list.size.toString())

        createRecyclerView()

        continueButtonTeams.setOnClickListener {
            prefsEditor.putBoolean("gameSettingsFlag", true)
            list = Array(teams.size) { MutableList(0) { "0.0" } }
            var teamsScores = Array(teamsAmount){0}
            val intent = Intent(this, GameSettings::class.java)
//            teams = teamsAdapter.getter().toMutableList()
//            intent.putExtra("teams", teams.toTypedArray())
//            intent.putExtra("settingsText", this.intent.getIntArrayExtra("settingsText"))
//            intent.putExtra("settingsInfo", this.intent.getBooleanArrayExtra("settingsInfo"))
//            for (i in teams.indices)
//                intent.putExtra("list$i", list[i].toTypedArray())
            prefsEditor.putInt("teamsAmount", teamsAmount)
            for (i in 0 until teamsAmount)
                prefsEditor.putString("team$i", teams[i])
            for (i in 0 until teamsAmount)
                prefsEditor.putInt("teamsScores$i", teamsScores[i])
            prefsEditor.apply()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

        }

        backButton.setOnClickListener {
            prefsEditor.putInt("teamsAmount", teamsAmount)
            for (i in 0 until teamsAmount)
                prefsEditor.putString("team$i", teams[i])
            prefsEditor.apply()
            finish()
        }

        addTeamButton.setOnClickListener {
            showAddTeamDialog()
        }
        if (currentPosition >= 2) {
            continueButtonTeams.isClickable = true
            continueButtonTeams.setTextColor(resources.getColor(R.color.activeButton))
        } else {
            continueButtonTeams.isClickable = false
            continueButtonTeams.setTextColor(resources.getColor(R.color.noActiveButton))
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
                teamsAmount--
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
                    teams[position] = tnld.editText?.text.toString().trim(' ')
                    teamsAdapter.notifyItemChanged(position)
                }
                tnet.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(p0: Editable?) {}

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                        if (tnld.editText?.text.toString().isEmpty() || tnld.editText?.text.toString()
                                .isBlank()
                        ) {
                            tnld.error = "Название не может быть пустым"
                            closeAddTeamDialog.isClickable = false
                            closeAddTeamDialog.background =
                                resources.getDrawable(R.drawable.add_team_no_active)
                        } else {
                            if (tnld.editText?.text.toString().length > 20) {
                                var counterEnd: Int = 0
                                var counterStart: Int = 0
                                while (tnld.editText?.text.toString()[tnld.editText?.text.toString().length - 1 - counterEnd] == ' ') {
                                    counterEnd++
                                }
                                while (tnld.editText?.text.toString()[counterStart] == ' ') counterStart++

                                if (counterEnd >= 1 || counterStart >=1) {
                                   if (counterEnd >= 5 || counterStart >=2) {
                                        tnld.error =
                                            "Молодец, ты нашёл пасхалку,a теперь убери пробелы"
                                        closeAddTeamDialog.isClickable = false
                                        closeAddTeamDialog.background =
                                            resources.getDrawable(R.drawable.add_team_no_active)
                                    }
                                    else
                                    {
                                        tnld.error ="Уберите свои пробелы"
                                        closeAddTeamDialog.isClickable = false
                                        closeAddTeamDialog.background =
                                            resources.getDrawable(R.drawable.add_team_no_active)

                                    }
                                } else {
                                    tnld.error = "Слишком длинное название"
                                    closeAddTeamDialog.isClickable = false
                                    closeAddTeamDialog.background =
                                        resources.getDrawable(R.drawable.add_team_no_active)
                                }
                            } else {
                                if (teams.contains(tnld.editText?.text.toString().trim(' '))) {
                                    tnld.error = "Такое название уже есть"
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
                        }
                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                        if (tnld.editText?.text.toString().isEmpty() || tnld.editText?.text.toString()
                                .isBlank()
                        ) {
                            tnld.error = "Название не может быть пустым"
                            closeAddTeamDialog.isClickable = false
                            closeAddTeamDialog.background =
                                resources.getDrawable(R.drawable.add_team_no_active)
                        } else {
                            if (tnld.editText?.text.toString().length > 20) {
                                var counterEnd: Int = 0
                                var counterStart: Int = 0
                                while (tnld.editText?.text.toString()[tnld.editText?.text.toString().length - 1 - counterEnd] == ' ') {
                                    counterEnd++
                                }
                                while (tnld.editText?.text.toString()[counterStart] == ' ') counterStart++

                                if (counterEnd >= 1 || counterStart >=1) {
                                     if (counterEnd >= 5 || counterStart >=2) {
                                         tnld.error =
                                             "Молодец, ты нашёл пасхалку,a теперь убери пробелы"
                                         closeAddTeamDialog.isClickable = false
                                         closeAddTeamDialog.background =
                                             resources.getDrawable(R.drawable.add_team_no_active)
                                     }
                                     else
                                     {
                                         tnld.error ="Уберите свои пробелы"
                                         closeAddTeamDialog.isClickable = false
                                         closeAddTeamDialog.background =
                                             resources.getDrawable(R.drawable.add_team_no_active)

                                     }
                                } else {
                                    tnld.error = "Слишком длинное название"
                                    closeAddTeamDialog.isClickable = false
                                    closeAddTeamDialog.background =
                                        resources.getDrawable(R.drawable.add_team_no_active)
                                }
                            } else {
                                if (teams.contains(tnld.editText?.text.toString().trim(' '))) {
                                    tnld.error = "Такое название уже есть"
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
            addTeam(tnld.editText?.text.toString().trim(' '), currentPosition++)
            if (currentPosition >= 2) {
                continueButtonTeams.isClickable = true
                continueButtonTeams.setTextColor(resources.getColor(R.color.activeButton))
            } else {
                continueButtonTeams.isClickable = false
                continueButtonTeams.setTextColor(resources.getColor(R.color.noActiveButton))
            }
            teamsAmount++
        }

        if (tnld.editText?.text.toString().isEmpty() || tnld.editText?.text.toString()
                .isBlank()
        ) {
            tnld.error = "Название не может быть пустым"
            closeAddTeamDialog.isClickable = false
            closeAddTeamDialog.background =
                resources.getDrawable(R.drawable.add_team_no_active)
        } else {
            if (tnld.editText?.text.toString().length > 20) {
                var counterEnd: Int = 0
                var counterStart: Int = 0
                while (tnld.editText?.text.toString()[tnld.editText?.text.toString().length - 1 - counterEnd] == ' ') {
                    counterEnd++
                }
                while (tnld.editText?.text.toString()[counterStart] == ' ') counterStart++

                if (counterEnd >= 1 || counterStart >=1) {
                     if (counterEnd >= 5 || counterStart >=2) {
                         tnld.error =
                             "Молодец, ты нашёл пасхалку,a теперь убери пробелы"
                         closeAddTeamDialog.isClickable = false
                         closeAddTeamDialog.background =
                             resources.getDrawable(R.drawable.add_team_no_active)
                     }
                     else
                     {
                         tnld.error ="Уберите свои пробелы"
                         closeAddTeamDialog.isClickable = false
                         closeAddTeamDialog.background =
                             resources.getDrawable(R.drawable.add_team_no_active)

                     }
                } else {
                    tnld.error = "Слишком длинное название"
                    closeAddTeamDialog.isClickable = false
                    closeAddTeamDialog.background =
                        resources.getDrawable(R.drawable.add_team_no_active)
                }
            } else {
                if (teams.contains(tnld.editText?.text.toString().trim(' '))) {
                    tnld.error = "Такое название уже есть"
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
        }

        tnet.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (tnld.editText?.text.toString().isEmpty() || tnld.editText?.text.toString()
                        .isBlank()
                ) {
                    tnld.error = "Название не может быть пустым"
                    closeAddTeamDialog.isClickable = false
                    closeAddTeamDialog.background =
                        resources.getDrawable(R.drawable.add_team_no_active)
                } else {
                    if (tnld.editText?.text.toString().length > 20) {
                        var counterEnd: Int = 0
                        var counterStart: Int = 0
                        while (tnld.editText?.text.toString()[tnld.editText?.text.toString().length - 1 - counterEnd] == ' ') {
                            counterEnd++
                        }
                        while (tnld.editText?.text.toString()[counterStart] == ' ') counterStart++

                        if (counterEnd >= 1 || counterStart >=1) {
                             if (counterEnd >= 5 || counterStart >=2) {
                                 tnld.error =
                                     "Молодец, ты нашёл пасхалку,a теперь убери пробелы"
                                 closeAddTeamDialog.isClickable = false
                                 closeAddTeamDialog.background =
                                     resources.getDrawable(R.drawable.add_team_no_active)
                             }
                             else
                             {
                                 tnld.error ="Уберите свои пробелы"
                                 closeAddTeamDialog.isClickable = false
                                 closeAddTeamDialog.background =
                                     resources.getDrawable(R.drawable.add_team_no_active)

                             }
                        } else {
                            tnld.error = "Слишком длинное название"
                            closeAddTeamDialog.isClickable = false
                            closeAddTeamDialog.background =
                                resources.getDrawable(R.drawable.add_team_no_active)
                        }
                    } else {
                        if (teams.contains(tnld.editText?.text.toString().trim(' '))) {
                            tnld.error = "Такое название уже есть"
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
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                if (tnld.editText?.text.toString().isEmpty() || tnld.editText?.text.toString()
                        .isBlank()
                ) {
                    tnld.error = "Название не может быть пустым"
                    closeAddTeamDialog.isClickable = false
                    closeAddTeamDialog.background =
                        resources.getDrawable(R.drawable.add_team_no_active)
                } else {
                    if (tnld.editText?.text.toString().length > 20) {
                        var counterEnd: Int = 0
                        var counterStart: Int = 0
                        while (tnld.editText?.text.toString()[tnld.editText?.text.toString().length - 1 - counterEnd] == ' ') {
                            counterEnd++
                        }
                        while (tnld.editText?.text.toString()[counterStart] == ' ') counterStart++

                        if (counterEnd >= 1 || counterStart >=1) {
                             if (counterEnd >= 5 || counterStart >=2) {
                                 tnld.error =
                                     "Молодец, ты нашёл пасхалку,a теперь убери пробелы"
                                 closeAddTeamDialog.isClickable = false
                                 closeAddTeamDialog.background =
                                     resources.getDrawable(R.drawable.add_team_no_active)
                             }
                             else
                             {
                                 tnld.error ="Уберите свои пробелы"
                                 closeAddTeamDialog.isClickable = false
                                 closeAddTeamDialog.background =
                                     resources.getDrawable(R.drawable.add_team_no_active)

                             }
                        } else {
                            tnld.error = "Слишком длинное название"
                            closeAddTeamDialog.isClickable = false
                            closeAddTeamDialog.background =
                                resources.getDrawable(R.drawable.add_team_no_active)
                        }
                    } else {
                        if (teams.contains(tnld.editText?.text.toString().trim(' '))) {
                            tnld.error = "Такое название уже есть"
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
                }
            }
        })
    }
}