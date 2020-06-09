package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_round.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class Round : AppCompatActivity() {
    var wordsNumber: Int = 0

    var currentWord = ""

    var currentWordNumber: Int = 0

    var words: MutableList<Int> = mutableListOf()

    var flagForFirstTap: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round)



        var settingsText: IntArray = this.intent.getIntArrayExtra("settingsText")
        var settingsInfo: BooleanArray = this.intent.getBooleanArrayExtra("settingsInfo")
        var teamNums: Int = this.intent.getIntExtra("teamsAmount", 2)
        var newRound: String = this.intent.getStringExtra("round")
        var teamsExtra = this.intent.getStringArrayExtra("teams")
        var wordList = this.intent.getIntExtra("book", -1)
        var teamsScores: IntArray = this.intent.getIntArrayExtra("teamsScores")
        var winnersIndex:Int=-1

        roundTitle.text = this.intent.getStringExtra("currentTeam")
        roundText.text = this.intent.getStringExtra("currentRound")

        val teamsNums = Array<Int>(teamNums) { it + 1 }
        var count = this.intent.getIntExtra("counter", 0)

        timerCounter.text = settingsText[1].toString()
        chronometer.base = (timerCounter.text.toString().toInt() * 1000).toLong()

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



                        if (max>=settingsText[0]) {
                            val intent = Intent(this, WinPage::class.java)
                            intent.putExtra("WinTeamName",teamsExtra[winnersIndex])
                            intent.putExtra("WinTeamScore",max)
                            startActivity(intent)
                            max=0
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                        }
                        else {
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

        check.setOnClickListener {
            check.isClickable = false
            if (flagForFirstTap) {
                knowWordsCounter.text = (knowWordsCounter.text.toString().toInt() + 1).toString()
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
                teamsScores[count]++
            }
            check.isClickable = true
        }

        cross.setOnClickListener {
            cross.isClickable = false
            if (flagForFirstTap) {
                skipWordsCounter.text = (skipWordsCounter.text.toString().toInt() + 1).toString()
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
                if (settingsInfo[0]) {
                    teamsScores[count]--
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
}