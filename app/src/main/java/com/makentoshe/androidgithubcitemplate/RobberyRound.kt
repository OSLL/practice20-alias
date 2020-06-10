package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_robbery_round.*
import kotlinx.android.synthetic.main.activity_round.backButton
import kotlinx.android.synthetic.main.activity_round.chronometer
import kotlinx.android.synthetic.main.activity_round.cross
import kotlinx.android.synthetic.main.activity_round.roundText
import kotlinx.android.synthetic.main.activity_round.roundTitle
import kotlinx.android.synthetic.main.activity_round.timerCounter
import kotlinx.android.synthetic.main.activity_round.word
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class RobberyRound : AppCompatActivity() {

    var wordsNumber: Int = 0
    var tasksNumber: Int = 0

    var currentWord = ""

    var currentWordNumber: Int = 0
    var currentTaskNumber: Int = 0

    var words: MutableList<Int> = mutableListOf()

    var flagForFirstTap: Boolean = false

    lateinit var robberyRoundAdapter: RobberyRoundAdapter

    lateinit var teamsExtra: Array<String>
    lateinit var teamsScores: IntArray
    var wordList: Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robbery_round)

        teamsExtra = this.intent.getStringArrayExtra("teams")
        teamsScores = this.intent.getIntArrayExtra("teamsScores")

        Log.e("Lol", "${teamsExtra[1]} ${teamsScores[1]}")
        var winnersIndex: Int = -1
        var teamNums: Int = this.intent.getIntExtra("teamsAmount", 2)
        var newRound: String = this.intent.getStringExtra("round")
        roundTitle.text = this.intent.getStringExtra("currentTeam")
        roundText.text = this.intent.getStringExtra("currentRound")
        var settingsText: IntArray = this.intent.getIntArrayExtra("settingsText")
        var settingsInfo: BooleanArray = this.intent.getBooleanArrayExtra("settingsInfo")
        wordList = this.intent.getIntExtra("book", -1)
        val teamsNums = Array<Int>(teamNums) { it + 1 }
        var count = this.intent.getIntExtra("counter", 0)

        timerCounter.text = settingsText[1].toString()
        chronometer.base = (timerCounter.text.toString().toInt() * 1000).toLong()

        createRecyclerView()

        if (!settingsInfo[2])
            taskRobberyText.visibility = View.GONE

        backButton.setOnClickListener {
            finish()
        }
        when (wordList) {
            0 -> {
                var file: InputStream = assets.open("Easy.txt")

                var bufferedReader = BufferedReader(InputStreamReader(file))

                while (bufferedReader.readLine() != null) wordsNumber++
                file.close()
            }
            1 -> {
                var file: InputStream = assets.open("Middle.txt")

                var bufferedReader = BufferedReader(InputStreamReader(file))

                while (bufferedReader.readLine() != null) wordsNumber++
                file.close()
            }
            2 -> {
                var file: InputStream = assets.open("Hard.txt")

                var bufferedReader = BufferedReader(InputStreamReader(file))

                while (bufferedReader.readLine() != null) wordsNumber++
                file.close()
            }
        }

        var max = -100000

        word.setOnClickListener {
            flagForFirstTap = true
            chronometer.start()
            word.isClickable = false
            if (settingsInfo[2]) {
                var fileTaskRobbery: InputStream = assets.open("Tasks.txt")
                var bufferedReaderTask = BufferedReader(InputStreamReader(fileTaskRobbery))
                while (bufferedReaderTask.readLine() != null) tasksNumber++
                fileTaskRobbery.close()
                fileTaskRobbery = assets.open("Tasks.txt")
                bufferedReaderTask = BufferedReader(InputStreamReader(fileTaskRobbery))
                currentTaskNumber = (0 until tasksNumber).random()
                for (i in 0 until currentTaskNumber) bufferedReaderTask.readLine()
                val currentTask: String = bufferedReaderTask.readLine()
                fileTaskRobbery.close()
                taskRobberyText.text = currentTask
            } else {
                taskRobberyText.visibility = View.GONE
            }
            chronometer.setOnChronometerTickListener {
                var elapsedMillis: Long = SystemClock.elapsedRealtime() - chronometer.base
                if (elapsedMillis > 1000) {
                    timerCounter.setText((timerCounter.text.toString().toInt() - 1).toString())
                    if (timerCounter.text.toString().toInt() == 0) {
                        chronometer.stop()
                        Toast.makeText(applicationContext, "Время вышло!", Toast.LENGTH_SHORT)
                            .show()
                        count += 1
                        Log.d("TAG", "$count")
                        if (count == teamNums) {
                            count = 0
                            newRound =
                                (newRound.substringBefore(" ").toInt() + 1).toString() + " раунд"


                            for (i in 0 until teamsExtra.size) {
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
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
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

                    elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
                }
            }

            when (wordList) {
                0 -> {
                    var file: InputStream = assets.open("Easy.txt")

                    var bufferedReader = BufferedReader(InputStreamReader(file))

                    word.text = newWord(file, bufferedReader)
                }
                1 -> {
                    var file: InputStream = assets.open("Middle.txt")

                    var bufferedReader = BufferedReader(InputStreamReader(file))

                    word.text = newWord(file, bufferedReader)
                }
                2 -> {
                    var file: InputStream = assets.open("Hard.txt")

                    var bufferedReader = BufferedReader(InputStreamReader(file))

                    word.text = newWord(file, bufferedReader)
                }
            }

        }


        cross.setOnClickListener {
            cross.isClickable = false
            if (flagForFirstTap) {
                when (wordList) {
                    0 -> {
                        var file: InputStream = assets.open("Easy.txt")

                        var bufferedReader = BufferedReader(InputStreamReader(file))

                        word.text = newWord(file, bufferedReader)
                    }
                    1 -> {
                        var file: InputStream = assets.open("Middle.txt")

                        var bufferedReader = BufferedReader(InputStreamReader(file))

                        word.text = newWord(file, bufferedReader)
                    }
                    2 -> {
                        var file: InputStream = assets.open("Hard.txt")

                        var bufferedReader = BufferedReader(InputStreamReader(file))

                        word.text = newWord(file, bufferedReader)
                    }
                }
            }
            cross.isClickable = true
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
    }

    private fun newWord(file: InputStream, bufferedReader: BufferedReader): String {

        currentWordNumber = (0 until wordsNumber).random()
        while (words.contains(currentWordNumber)) {
            if (currentWordNumber == 49) currentWordNumber = 0
            currentWordNumber++
        }
        words.add(currentWordNumber)
        if (words.size == 50) {
            words.removeAll(words)
            Log.e("Error", "${words.size}")
        }
        for (i in 0 until currentWordNumber) bufferedReader.readLine()
        currentWord = bufferedReader.readLine()

        file.close()
        return currentWord
    }

    fun createRecyclerView() {
        var teamsScores1: IntArray = IntArray(teamsExtra.size) { 0 }
        robberyRoundAdapter = RobberyRoundAdapter(this, teamsExtra, teamsScores1)
        robberyRoundRecycler.adapter = robberyRoundAdapter
        robberyRoundRecycler.layoutManager = LinearLayoutManager(this)

        robberyRoundAdapter.setOnItemClickListener(object :
            RobberyRoundAdapter.onItemClickListener {

            override fun onCheckClicked(position: Int) {
                if (flagForFirstTap) {
                    when (wordList) {
                        0 -> {
                            var file: InputStream = assets.open("Easy.txt")

                            var bufferedReader = BufferedReader(InputStreamReader(file))

                            word.text = newWord(file, bufferedReader)
                        }
                        1 -> {
                            var file: InputStream = assets.open("Middle.txt")

                            var bufferedReader = BufferedReader(InputStreamReader(file))

                            word.text = newWord(file, bufferedReader)
                        }
                        2 -> {
                            var file: InputStream = assets.open("Hard.txt")

                            var bufferedReader = BufferedReader(InputStreamReader(file))

                            word.text = newWord(file, bufferedReader)
                        }
                    }
                    teamsScores[position]++
                    teamsScores1[position]++
                    robberyRoundAdapter.notifyItemChanged(position)
                }
            }

        })

    }
}
