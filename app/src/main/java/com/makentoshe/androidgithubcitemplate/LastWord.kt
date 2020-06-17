package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_robbery_round.*
import kotlinx.android.synthetic.main.activity_round.backButton
import kotlinx.android.synthetic.main.activity_round.cross

class LastWord : AppCompatActivity() {

//    lateinit var list: Array<MutableList<String>>

    var wordsNumber: Int = 0

    var currentWord = ""

    var currentWordNumber: Int = 0

    var words: MutableList<Int> = mutableListOf()

    lateinit var robberyRoundAdapter: RobberyRoundAdapter

    lateinit var teams: Array<String>
    lateinit var teamsScores: Array<Int>
    var book: Int = -1
    var teamsAmount: Int = 0
    var currentRoundText: String = "0"
    var currentTeamText:String = "0"
    var wordsForWin =0

    var teamsNums: Array<Int> = arrayOf()
    var max = -100000
    var winnersIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_word)

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

        teamsAmount = appPrefs.getInt("teamsAmount",2)
        currentRoundText = appPrefs.getString("currentRoundText","1 раунд").toString()
        currentTeamText = appPrefs.getString("currentTeamText", "Error").toString()
        wordsForWin = appPrefs.getInt("wordsForWin", 10)
        teamsNums = Array<Int>(teamsAmount) { it + 1 }
        var counter = appPrefs.getInt("counter", -1)

        word.text = appPrefs.getString("currentWord","Error")
        teams = Array(teamsAmount){""}
        teamsScores = Array(teamsAmount){0}
        for (i in teams.indices)
            teams[i] = appPrefs.getString("team$i", "").toString()
        for (i in teams.indices)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)

        for (i in teams.indices)
            Log.e("Any", teamsScores[i].toString())
        book = appPrefs.getInt("book", -1)

//        list = Array(teams.size) { MutableList(0) { "0.0" } }
//        for (i in teams.indices)
//            list[i] = this.intent.getStringArrayExtra("list$i")!!.toMutableList()




        fun createRecyclerView() {
            var teamsScores1 = IntArray(teams.size) { 0 }
            robberyRoundAdapter = RobberyRoundAdapter(this, teams, teamsScores1)
            robberyRoundRecycler.adapter = robberyRoundAdapter
            robberyRoundRecycler.layoutManager = LinearLayoutManager(this)



            robberyRoundAdapter.setOnItemClickListener(object :
                RobberyRoundAdapter.onItemClickListener {

                override fun onCheckClicked(position: Int) {


                    teamsScores[position]++
                    teamsScores1[position]++
                    for (i in teams.indices)
                        Log.e("Any", teamsScores[i].toString())
//                    list[position][roundText.text.toString().dropLast(6).toInt() - 1] =
//                        "${((list[position][roundText.text.toString().dropLast(6)
//                            .toInt() - 1].substringBefore('.')
//                            .toInt()) + 1)}.${list[position][roundText.text.toString()
//                            .dropLast(6).toInt() - 1].substringAfter('.').toInt()}"
                    robberyRoundAdapter.notifyItemChanged(position)
                    if (counter == 0) {

                        for (i in teams.indices) {
                            if (teamsScores[i] > max) {
                                max = teamsScores[i]
                                winnersIndex = i
                            }
                        }

                    }

                    if (max >= wordsForWin) {
                        val intent = Intent(this@LastWord, WinPage::class.java)
                        prefsEditor.putInt("max", max)
                        prefsEditor.putString("winner", teams[winnersIndex])
                        for (i in 0 until teamsAmount)
                            prefsEditor.putInt("teamsScores$i", teamsScores[i])
                        prefsEditor.putInt("counter", counter)
                        prefsEditor.putString("currentRoundText", currentRoundText)
                        prefsEditor.apply()
//                        intent.putExtra("WinTeamName", teams[winnersIndex])
//                        intent.putExtra("WinTeamScore", max)
//                        intent.putExtra("teams", teams)
//                        for (i in teams.indices)
//                            intent.putExtra("list$i", list[i].toTypedArray())
                        startActivity(intent)
                        max = 0
                        finish()
                    } else {
                        var currentTeamText: String = teamsNums[counter].toString() + " команда"

                        val intent = Intent(this@LastWord, Game::class.java)
                        for (i in 0 until teamsAmount)
                            prefsEditor.putInt("teamsScores$i", teamsScores[i])
                        prefsEditor.putInt("counter", counter)
                        prefsEditor.putString("currentRoundText", currentRoundText)
                        prefsEditor.putString("currentTeamText", currentTeamText)
                        prefsEditor.apply()
//                        intent.putExtra("currentRoundText", currentRoundText)
//                        intent.putExtra("currentTeamText", currentTeamText)
//                        intent.putExtra("settingsText", settingsText)
//                        intent.putExtra("settingsInfo", settingsInfo)
//                        intent.putExtra("teams", teams)
//                        intent.putExtra("counter", counter)
//                        intent.putExtra("teamsScores", teamsScores)
//                        intent.putExtra("book", book)
//                        for (i in teams.indices)
//                            intent.putExtra("list$i", list[i].toTypedArray())
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                        finish()
                    }
                }
            })
        }
        createRecyclerView()

        backButton.setOnClickListener {
            finish()
        }

        cross.setOnClickListener {
            if (counter == 0) {
//                currentRoundText =
//                    (currentRoundText.substringBefore(" ")
//                        .toInt() + 1).toString() + " раунд"
                for (i in teams.indices) {
                    if (teamsScores[i] > max) {
                        max = teamsScores[i]
                        winnersIndex = i
                    }
                }
            }

            if (max >= wordsForWin) {
                val intent = Intent(this, WinPage::class.java)
                prefsEditor.putInt("max", max)
                prefsEditor.putString("winner", teams[winnersIndex])
                for (i in 0 until teamsAmount)
                    prefsEditor.putInt("teamsScores$i", teamsScores[i])
                prefsEditor.putInt("counter", counter)
                prefsEditor.putString("currentRoundText", currentRoundText)
                prefsEditor.apply()
//                intent.putExtra("WinTeamName", teams[winnersIndex])
//                intent.putExtra("WinTeamScore", max)
//                intent.putExtra("teams", teams)
//                for (i in teams.indices)
//                    intent.putExtra("list$i", list[i].toTypedArray())
                startActivity(intent)
                max = 0
                finish()
            } else {
                var currentTeamText: String = teamsNums[counter].toString() + " команда"
                val intent = Intent(this, Game::class.java)
                for (i in 0 until teamsAmount)
                    prefsEditor.putInt("teamsScores$i", teamsScores[i])
                prefsEditor.putInt("counter", counter)
                prefsEditor.putString("currentRoundText", currentRoundText)
                prefsEditor.putString("currentTeamText", currentTeamText)
                prefsEditor.apply()
//                intent.putExtra("currentRoundText", currentRoundText)
//                intent.putExtra("newTeam", newTeam)
////                intent.putExtra("settingsText", settingsText)
////                intent.putExtra("settingsInfo", settingsInfo)
//                intent.putExtra("teams", teams)
//                intent.putExtra("counter", counter)
//                intent.putExtra("teamsScores", teamsScores)
//                intent.putExtra("book", book)
////                for (i in teams.indices)
////                    intent.putExtra("list$i", list[i].toTypedArray())
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                finish()
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
    }
}
