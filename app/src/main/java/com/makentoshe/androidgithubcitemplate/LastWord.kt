package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_robbery_round.*
import kotlinx.android.synthetic.main.activity_round.backButton
import kotlinx.android.synthetic.main.activity_round.cross
import kotlinx.android.synthetic.main.activity_round.roundText
import kotlinx.android.synthetic.main.activity_round.roundTitle

class LastWord : AppCompatActivity() {

    var wordsNumber: Int = 0

    var currentWord = ""

    var currentWordNumber: Int = 0

    var words: MutableList<Int> = mutableListOf()

    lateinit var robberyRoundAdapter: RobberyRoundAdapter

    lateinit var teamsExtra: Array<String>
    lateinit var teamsScores: IntArray
    var wordList: Int = -1
    var teamNums: Int = 0
    var newRound: String = "0"
    var settingsText: IntArray = intArrayOf()
    var settingsInfo: BooleanArray = booleanArrayOf()
    var word1: String = "0"
   var  teamsNums: Array<Int> = arrayOf()
    var count = 0
    var max = -100000
    var winnersIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_word)

         teamNums = this.intent.getIntExtra("teamsAmount", 2)
         newRound  = this.intent.getStringExtra("newRound")
         settingsText  = this.intent.getIntArrayExtra("settingsText")
         settingsInfo = this.intent.getBooleanArrayExtra("settingsInfo")
         word1 = this.intent.getStringExtra("currentWord")
         teamsNums = Array<Int>(teamNums) { it + 1 }
         count = this.intent.getIntExtra("counter", 0)

        word.text=word1

        teamsExtra = this.intent.getStringArrayExtra("teams")
        teamsScores = this.intent.getIntArrayExtra("teamsScores")

        roundTitle.text = this.intent.getStringExtra("currentTeam")
        roundText.text = this.intent.getStringExtra("currentRound")

        wordList = this.intent.getIntExtra("book", -1)






        fun createRecyclerView() {
            var teamsScores1 = IntArray(teamsExtra.size) { 0 }
            robberyRoundAdapter = RobberyRoundAdapter(this, teamsExtra, teamsScores1)
            robberyRoundRecycler.adapter = robberyRoundAdapter
            robberyRoundRecycler.layoutManager = LinearLayoutManager(this)



            robberyRoundAdapter.setOnItemClickListener(object :
                RobberyRoundAdapter.onItemClickListener {

                override fun onCheckClicked(position: Int) {


                    teamsScores[position]++
                    teamsScores1[position]++
                    robberyRoundAdapter.notifyItemChanged(position)
                        if (count == 0) {

                            for (i in teamsExtra.indices) {
                                if (teamsScores[i] > max) {
                                    max = teamsScores[i]
                                    winnersIndex = i
                                }
                            }

                        }

                        if (max >= settingsText[0]) {
                            val intent = Intent(this@LastWord, WinPage::class.java)
                            intent.putExtra("WinTeamName", teamsExtra[winnersIndex])
                            intent.putExtra("WinTeamScore", max)
                            startActivity(intent)
                            max = 0
                            finish()
                        } else {
                            var newTeam: String = teamsNums[count].toString() + " команда"

                            val intent = Intent(this@LastWord, Game::class.java)
                            intent.putExtra("newRound", newRound)
                            intent.putExtra("newTeam", newTeam)
                            intent.putExtra("settingsText", settingsText)
                            intent.putExtra("settingsInfo", settingsInfo)
                            intent.putExtra("teams", teamsExtra)
                            intent.putExtra("counter", count)
                            intent.putExtra("teamsScores", teamsScores)
                            intent.putExtra("book", wordList)
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
            if (count == 0) {
//                newRound =
//                    (newRound.substringBefore(" ")
//                        .toInt() + 1).toString() + " раунд"
                for (i in teamsExtra.indices) {
                    if (teamsScores[i] > max) {
                        max = teamsScores[i]
                        winnersIndex = i
                    }
                }
            }

            if (max >= settingsText[0]) {
                val intent = Intent(this, WinPage::class.java)
                intent.putExtra("WinTeamName", teamsExtra[winnersIndex])
                intent.putExtra("WinTeamScore", max)
                startActivity(intent)
                max = 0
                finish()
            } else {
                var newTeam: String = teamsNums[count].toString() + " команда"
                val intent = Intent(this, Game::class.java)
                intent.putExtra("newRound", newRound)
                intent.putExtra("newTeam", newTeam)
                intent.putExtra("settingsText", settingsText)
                intent.putExtra("settingsInfo", settingsInfo)
                intent.putExtra("teams", teamsExtra)
                intent.putExtra("counter", count)
                intent.putExtra("teamsScores", teamsScores)
                intent.putExtra("book", wordList)
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