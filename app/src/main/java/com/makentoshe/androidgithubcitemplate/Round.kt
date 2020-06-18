package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_round.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class Round : AppCompatActivity() {

    lateinit var list: Array<MutableList<String>>

    private var wordsNumber: Int = 0
    private var tasksNumber: Int = 0
    lateinit var teams: Array<String>

    private var currentWord = ""
    lateinit var teamsScores: Array<Int>

    private var currentWordNumber: Int = 0
    private var currentTaskNumber: Int = 0

    private var words: MutableList<Int> = mutableListOf()

    private var flagForFirstTap: Boolean = false

    private var flagForLastWord: Boolean = false

    private var flagForPause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round)

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

        lastWord.visibility = View.INVISIBLE

        pauseButton.isClickable = false

        val teamsAmount: Int = appPrefs.getInt("teamsAmount", 0)
        teams = Array(teamsAmount) { "" }
        var currentRoundText: String = appPrefs.getString("currentRoundText", "1 раунд").toString()
        val currentTeamText: String = appPrefs.getString("currentTeamText", "1 команда").toString()
        for (i in 0 until teamsAmount)
            teams[i] = appPrefs.getString("team$i", "").toString()
        val book = appPrefs.getInt("book", -1)
        teamsScores = Array(teamsAmount) { 0 }
        for (i in 0 until teamsAmount)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)
        val roundLength = appPrefs.getInt("roundLength", 10)
        val wordsForWin = appPrefs.getInt("wordsForWin", 10)
        val fineChanger = appPrefs.getBoolean("fineChanger", false)
        val generalLast = appPrefs.getBoolean("generalLast", false)
        val tasks = appPrefs.getBoolean("tasks", false)
        var isPlaying = false
        var winnersIndex: Int = -1
        var counter = appPrefs.getInt("counter", -1)
        var timeLeftMilliseconds:Long=(roundLength.toLong()*1000)
        var roundNumber = appPrefs.getInt("roundNumber", 0)

        class MyCountDownTimer(timeLeftMilliseconds:Long, interval:Long): CountDownTimer(timeLeftMilliseconds, interval){

            override fun onTick(p0: Long) {
                timeLeftMilliseconds=p0
                timerCounter.text=(timeLeftMilliseconds/1000).toString()
            }

            override fun onFinish() {
                pauseButton.isClickable = false
                Toast.makeText(applicationContext, "Время вышло!", Toast.LENGTH_SHORT)
                    .show()
                counter += 1

                flagForLastWord = true
                if (generalLast) {
                    if (counter == teamsAmount) {
                        counter = 0
                        currentRoundText =
                            (currentRoundText.substringBefore(" ")
                                .toInt() + 1).toString() + " раунд"
                    }
                    prefsEditor.putString("currentWord", word.text.toString())
                    prefsEditor.putInt("roundNumber", roundNumber)
                    for (i in 0 until teamsAmount)
                        prefsEditor.putInt("teamsScores$i", teamsScores[i])
                    for (i in 0 until teamsAmount)
                        for (j in 0 until roundNumber)
                            prefsEditor.putString("list[$i][$j]", list[i][j])
                    prefsEditor.putInt("counter", counter)
                    prefsEditor.putString("currentRoundText", currentRoundText)
                    prefsEditor.apply()
                    val intent = Intent(this@Round, LastWord::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                    finish()
                }
                lastWord.visibility = View.VISIBLE
            }

        }
        var countDownTimer =  MyCountDownTimer(timeLeftMilliseconds, 1000)

        roundTitle.text = currentTeamText
        roundText.text = currentRoundText

        val teamsNums = Array(teamsAmount) { it + 1 }

        if (counter == 0)
            roundNumber++
        Log.e("sas",roundNumber.toString())
        list = Array(teams.size) { MutableList(roundNumber) { "0.0" } }

        for (i in 0 until teamsAmount)
            for (j in 0 until roundNumber)
                list[i][j] = appPrefs.getString("list[$i][$j]", "0.0").toString()

        timerCounter.text = roundLength.toString()

        backButton.setOnClickListener {
            finish()
        }

        if (!tasks)
            taskText.visibility = View.GONE

        when (book) {
            0 -> {
                val file: InputStream = assets.open("Easy.txt")
                val bufferedReader = BufferedReader(InputStreamReader(file))
                while (bufferedReader.readLine() != null) {
                    wordsNumber++
                }
                file.close()
            }
            1 -> {
                val file: InputStream = assets.open("Middle.txt")
                val bufferedReader = BufferedReader(InputStreamReader(file))
                while (bufferedReader.readLine() != null) {
                    wordsNumber++
                }
                file.close()
            }
            2 -> {
                val file: InputStream = assets.open("Hard.txt")
                val bufferedReader = BufferedReader(InputStreamReader(file))
                while (bufferedReader.readLine() != null) {
                    wordsNumber++
                }
                file.close()
            }
        }

        var max = -100000

        pauseButton.setOnClickListener {
            if (flagForPause) {
                if (isPlaying) {
                    pauseButton.background = resources.getDrawable(R.drawable.medium_level_button)
                    check.isClickable = false
                    cross.isClickable = false
                    word.text = "Пауза"

                    countDownTimer.cancel()
                    isPlaying = false
                } else {
                    pauseButton.background = resources.getDrawable(R.drawable.hard_level_button)
                    check.isClickable = true
                    cross.isClickable = true
                    when (book) {
                        0 -> {
                            val file: InputStream = assets.open("Easy.txt")
                            val bufferedReader = BufferedReader(InputStreamReader(file))
                            word.text = newWord(bufferedReader)
                        }
                        1 -> {
                            val file: InputStream = assets.open("Middle.txt")
                            val bufferedReader = BufferedReader(InputStreamReader(file))
                            word.text = newWord(bufferedReader)
                        }
                        2 -> {
                            val file: InputStream = assets.open("Hard.txt")
                            val bufferedReader = BufferedReader(InputStreamReader(file))
                            word.text = newWord(bufferedReader)
                        }
                    }
                    countDownTimer =  MyCountDownTimer(timeLeftMilliseconds, 1000)
                    countDownTimer.start()
                    isPlaying = true
                }
            }
        }

        word.setOnClickListener {
            flagForFirstTap = true
            flagForPause = true
            countDownTimer.start()
            isPlaying = true
            word.isClickable = false
            if (tasks) {
                var fileTask: InputStream = assets.open("Tasks.txt")
                var bufferedReaderTask = BufferedReader(InputStreamReader(fileTask))
                while (bufferedReaderTask.readLine() != null) tasksNumber++
                fileTask.close()
                currentTaskNumber = (0 until tasksNumber).random()
                fileTask = assets.open("Tasks.txt")
                bufferedReaderTask = BufferedReader(InputStreamReader(fileTask))
                for (i in 0 until currentTaskNumber) bufferedReaderTask.readLine()
                val currentTask: String = bufferedReaderTask.readLine()
                fileTask.close()
                taskText.text = currentTask
            } else {
                taskText.visibility = View.GONE
            }

            when (book) {
                0 -> {
                    val file: InputStream = assets.open("Easy.txt")
                    val bufferedReader = BufferedReader(InputStreamReader(file))
                    word.text = newWord(bufferedReader)
                }
                1 -> {
                    val file: InputStream = assets.open("Middle.txt")
                    val bufferedReader = BufferedReader(InputStreamReader(file))
                    word.text = newWord(bufferedReader)
                }
                2 -> {
                    val file: InputStream = assets.open("Hard.txt")
                    val bufferedReader = BufferedReader(InputStreamReader(file))
                    word.text = newWord(bufferedReader)
                }
            }

            check.setOnClickListener {
                check.isClickable = false
                if (flagForFirstTap) {
                    if (flagForLastWord) {
                            teamsScores[counter - 1]++
                            list[counter-1][roundNumber - 1] =
                                "${((list[counter-1][roundNumber - 1].substringBefore('.')
                                    .toInt()) + 1)}.${list[counter-1][roundNumber - 1].substringAfter('.')}"

                        if (counter == teamsAmount) {
                            counter = 0
                            currentRoundText =
                                (currentRoundText.substringBefore(" ")
                                    .toInt() + 1).toString() + " раунд"


                            for (i in teams.indices) {
                                if (teamsScores[i] > max) {
                                    max = teamsScores[i]
                                    winnersIndex = i
                                }
                            }

                        }


                        if (max >= wordsForWin) {
                            val intent = Intent(this, WinPage::class.java)
                            prefsEditor.putInt("roundNumber", roundNumber)
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
                            val intent = Intent(this, Game::class.java)
                            prefsEditor.putInt("roundNumber", roundNumber)
                            for (i in 0 until teamsAmount)
                                prefsEditor.putInt("teamsScores$i", teamsScores[i])
                            prefsEditor.putInt("counter", counter)
                            prefsEditor.putString("currentRoundText", currentRoundText)
                            for (i in 0 until teamsAmount)
                                for (j in 0 until roundNumber)
                                    prefsEditor.putString("list[$i][$j]", list[i][j])
                            prefsEditor.apply()
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                            finish()

                        }
                    }
                    knowWordsCounter.text =
                        (knowWordsCounter.text.toString().toInt() + 1).toString()
                    when (book) {
                        0 -> {
                            val file: InputStream = assets.open("Easy.txt")
                            val bufferedReader = BufferedReader(InputStreamReader(file))
                            word.text = newWord(bufferedReader)
                        }
                        1 -> {
                            val file: InputStream = assets.open("Middle.txt")
                            val bufferedReader = BufferedReader(InputStreamReader(file))
                            word.text = newWord(bufferedReader)
                        }
                        2 -> {
                            val file: InputStream = assets.open("Hard.txt")
                            val bufferedReader = BufferedReader(InputStreamReader(file))
                            word.text = newWord(bufferedReader)
                        }
                    }
                    teamsScores[counter]++
                    list[counter][roundNumber - 1] =
                        "${((list[counter][roundNumber - 1].substringBefore('.')
                            .toInt()) + 1)}.${list[counter][roundNumber - 1].substringAfter('.')}"
                }
                check.isClickable = true
            }
        }

        cross.setOnClickListener {
            cross.isClickable = false
            if (flagForFirstTap) {
                if (flagForLastWord) {

                    if (fineChanger) {
                            teamsScores[counter - 1]--
                    }
                        list[counter-1][roundNumber - 1] =
                            "${((list[counter-1][roundNumber - 1].substringBefore('.')
                                .toInt()))}.${list[counter-1][roundNumber - 1].substringAfter('.').toInt() + 1}"

                    if (counter == teamsAmount) {
                        counter = 0
                        currentRoundText =
                            (currentRoundText.substringBefore(" ")
                                .toInt() + 1).toString() + " раунд"


                        for (i in teams.indices) {
                            if (teamsScores[i] > max) {
                                max = teamsScores[i]
                                winnersIndex = i
                            }
                        }
                    }


                    if (max >= wordsForWin) {
                        val intent = Intent(this, WinPage::class.java)
                        prefsEditor.putInt("roundNumber", roundNumber)
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
                        teamsNums[counter].toString() + " команда"
                        val intent = Intent(this, Game::class.java)
                        prefsEditor.putInt("roundNumber", roundNumber)
                        for (i in 0 until teamsAmount)
                            prefsEditor.putInt("teamsScores$i", teamsScores[i])
                        prefsEditor.putInt("counter", counter)
                        prefsEditor.putString("currentRoundText", currentRoundText)
                        for (i in 0 until teamsAmount)
                            for (j in 0 until roundNumber)
                                prefsEditor.putString("list[$i][$j]", list[i][j])
                        prefsEditor.apply()
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                        finish()
                    }
                }
                skipWordsCounter.text = (skipWordsCounter.text.toString().toInt() + 1).toString()
                when (book) {
                    0 -> {
                        val file: InputStream = assets.open("Easy.txt")
                        val bufferedReader = BufferedReader(InputStreamReader(file))
                        word.text = newWord(bufferedReader)
                    }
                    1 -> {
                        val file: InputStream = assets.open("Middle.txt")
                        val bufferedReader = BufferedReader(InputStreamReader(file))
                        word.text = newWord(bufferedReader)
                    }
                    2 -> {
                        val file: InputStream = assets.open("Hard.txt")
                        val bufferedReader = BufferedReader(InputStreamReader(file))
                        word.text = newWord(bufferedReader)
                    }
                }
                if (fineChanger) {
                    teamsScores[counter]--
                }
            }
            cross.isClickable = true
            list[counter][roundNumber - 1] =
                "${((list[counter][roundNumber - 1].substringBefore('.')
                    .toInt()))}.${list[counter][roundNumber - 1].substringAfter('.').toInt() + 1}"
        }
    }

    override fun finish() {

    }

    private fun newWord(bufferedReader: BufferedReader): String {

        currentWordNumber = (0 until wordsNumber).random()
        while (words.contains(currentWordNumber)) {
            if (currentWordNumber == wordsNumber - 1) currentWordNumber = 0
            currentWordNumber++
        }
        words.add(currentWordNumber)
        if (words.size == wordsNumber) {
            words.removeAll(words)
            Log.e("Error", "$wordsNumber")
        }
        for (i in 1 until currentWordNumber) bufferedReader.readLine()
        currentWord = bufferedReader.readLine()
        return currentWord
    }
}