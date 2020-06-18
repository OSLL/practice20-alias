package com.threemonkeys.alias

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_robbery_round.*
import kotlinx.android.synthetic.main.activity_round.cross

class LastWord : AppCompatActivity() {

    lateinit var list: Array<MutableList<String>>
    lateinit var robberyRoundAdapter: RobberyRoundAdapter
    lateinit var teams: Array<String>
    lateinit var teamsScores: Array<Int>
    private var book: Int = -1
    var teamsAmount: Int = 0
    var currentRoundText: String = "0"
    private var currentTeamText: String = "0"
    var wordsForWin = 0

    var teamsNumbers: Array<Int> = arrayOf()
    var max = -100000
    var winnersIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_word)

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

        teamsAmount = appPrefs.getInt("teamsAmount", 2)
        currentRoundText = appPrefs.getString("currentRoundText", "1 раунд").toString()
        currentTeamText = appPrefs.getString("currentTeamText", "Error").toString()
        wordsForWin = appPrefs.getInt("wordsForWin", 10)
        teamsNumbers = Array(teamsAmount) { it + 1 }
        val counter = appPrefs.getInt("counter", -1)
        word.text = appPrefs.getString("currentWord", "Error")
        roundTitle.text=currentTeamText
        if (counter==0) {
            roundText.text = (currentRoundText.substringBefore(" ").toInt() - 1).toString() + " раунд"
        }else{
            roundText.text = currentRoundText
        }
        teams = Array(teamsAmount) { "" }
        teamsScores = Array(teamsAmount) { 0 }
        for (i in teams.indices)
            teams[i] = appPrefs.getString("team$i", "").toString()
        for (i in teams.indices)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)
        book = appPrefs.getInt("book", -1)
        val roundNumber = appPrefs.getInt("roundNumber", 0)
        list = Array(teams.size) { MutableList(roundNumber) { "0.0" } }
        for (i in 0 until teamsAmount)
            for (j in 0 until roundNumber)
                list[i][j] = appPrefs.getString("list[$i][$j]", "0.0").toString()

        fun createRecyclerView() {
            val teamsScores1 = IntArray(teams.size) { 0 }
            robberyRoundAdapter = RobberyRoundAdapter(this, teams, teamsScores1)
            robberyRoundRecycler.adapter = robberyRoundAdapter
            robberyRoundRecycler.layoutManager = LinearLayoutManager(this)

            robberyRoundAdapter.setOnItemClickListener(object :
                RobberyRoundAdapter.OnItemClickListener {

                override fun onCheckClicked(position: Int) {
                    teamsScores[position]++
                    teamsScores1[position]++
                    list[position][roundNumber - 1] =
                        "${((list[position][roundNumber - 1].substringBefore('.')
                            .toInt()) + 1)}.${list[position][roundNumber - 1].substringAfter('.')
                            .toInt()}"
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
                        for (i in 0 until teamsAmount)
                            for (j in 0 until roundNumber)
                                prefsEditor.putString("list[$i][$j]", list[i][j])
                        prefsEditor.apply()
                        startActivity(intent)
                        max = 0
                        finish()
                    } else {
                        val currentTeamText: String = teamsNumbers[counter].toString() + " команда"
                        val intent = Intent(this@LastWord, Game::class.java)
                        for (i in 0 until teamsAmount)
                            prefsEditor.putInt("teamsScores$i", teamsScores[i])
                        prefsEditor.putInt("counter", counter)
                        prefsEditor.putString("currentRoundText", currentRoundText)
                        prefsEditor.putString("currentTeamText", currentTeamText)
                        for (i in 0 until teamsAmount)
                            for (j in 0 until roundNumber)
                                prefsEditor.putString("list[$i][$j]", list[i][j])
                        prefsEditor.apply()
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

        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды

        cross.setOnClickListener {
            if (counter == 0) {
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
                for (i in 0 until teamsAmount)
                    for (j in 0 until roundNumber)
                        prefsEditor.putString("list[$i][$j]", list[i][j])
                prefsEditor.apply()
                startActivity(intent)
                max = 0
                finish()
            } else {
                val currentTeamText: String = teamsNumbers[counter].toString() + " команда"
                val intent = Intent(this, Game::class.java)
                for (i in 0 until teamsAmount)
                    prefsEditor.putInt("teamsScores$i", teamsScores[i])
                prefsEditor.putInt("counter", counter)
                prefsEditor.putString("currentRoundText", currentRoundText)
                prefsEditor.putString("currentTeamText", currentTeamText)
                for (i in 0 until teamsAmount)
                    for (j in 0 until roundNumber)
                        prefsEditor.putString("list[$i][$j]", list[i][j])
                prefsEditor.apply()
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                finish()
            }
        }
    }

    override fun finish() {}
}
