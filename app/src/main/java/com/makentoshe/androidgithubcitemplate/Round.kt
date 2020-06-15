package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
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

    private var currentWord = ""

    private var currentWordNumber: Int = 0
    private var currentTaskNumber: Int = 0

    private var words: MutableList<Int> = mutableListOf()

    private var flagForFirstTap: Boolean = false

    var flagForLastWord: Boolean = false

    var flagForPause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round)

        lastWord.visibility= View.INVISIBLE

        var settingsText: IntArray = this.intent.getIntArrayExtra("settingsText")!!
        var settingsInfo: BooleanArray = this.intent.getBooleanArrayExtra("settingsInfo")!!
        var teamNums: Int = this.intent.getIntExtra("teamsAmount", 2)
        var newRound: String = this.intent.getStringExtra("round")!!
        var teamsExtra = this.intent.getStringArrayExtra("teams")
        var wordList = this.intent.getIntExtra("book", -1)
        var teamsScores: IntArray = this.intent.getIntArrayExtra("teamsScores")!!
        var isPlaying = false
        var winnersIndex: Int = -1

        list = Array(teamsExtra.size) { MutableList(0) { "0.0" } }
        for (i in teamsExtra.indices)
            list[i] = this.intent.getStringArrayExtra("list$i")!!.toMutableList()

        roundTitle.text = this.intent.getStringExtra("currentTeam")
        roundText.text = this.intent.getStringExtra("currentRound")

        val teamsNums = Array(teamNums) { it + 1 }
        var count = this.intent.getIntExtra("counter", 0)

        if (count == 0)
            for (i in list) i.add("0.0")

        timerCounter.text = settingsText[1].toString()
        chronometer.base = (timerCounter.text.toString().toInt() * 1000).toLong()

        backButton.setOnClickListener {
            finish()
        }

        if (!settingsInfo[2])
            taskText.visibility = View.GONE

        when (wordList) {
            0 -> {
                var file: InputStream = assets.open("Easy.txt")

                var bufferedReader = BufferedReader(InputStreamReader(file))

                while (bufferedReader.readLine() != null) {
                    wordsNumber++
                    Log.e("Sas", wordsNumber.toString())
                }
                file.close()
            }
            1 -> {
                var file: InputStream = assets.open("Middle.txt")

                var bufferedReader = BufferedReader(InputStreamReader(file))

                while (bufferedReader.readLine() != null) {
                    wordsNumber++
                    Log.e("Sas", wordsNumber.toString())
                }
                file.close()
            }
            2 -> {
                var file: InputStream = assets.open("Hard.txt")

                var bufferedReader = BufferedReader(InputStreamReader(file))

                while (bufferedReader.readLine() != null) {
                    wordsNumber++
                    Log.e("Sas", wordsNumber.toString())
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
                    chronometer.stop()
                    isPlaying = false
                } else {
                    pauseButton.background = resources.getDrawable(R.drawable.hard_level_button)
                    check.isClickable = true
                    cross.isClickable = true
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
                    chronometer.start()
                    isPlaying = true
                }
            }
        }

        word.setOnClickListener {
            flagForFirstTap = true
            flagForPause=true
            chronometer.start()
            isPlaying = true
            word.isClickable = false
            if (settingsInfo[2]) {
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

            chronometer.setOnChronometerTickListener {
                var elapsedMillis: Long = SystemClock.elapsedRealtime() - chronometer.base
                if (elapsedMillis > 1000) {
                    timerCounter.text = (timerCounter.text.toString().toInt() - 1).toString()
                    if (timerCounter.text.toString().toInt() <= 0) {
                        chronometer.stop()
                        Toast.makeText(applicationContext, "Время вышло!", Toast.LENGTH_SHORT)
                            .show()
                        count += 1

                        flagForLastWord = true
                        if (settingsInfo[1]) {
                            if (count == teamNums) {
                                count = 0
                                newRound =
                                    (newRound.substringBefore(" ")
                                        .toInt() + 1).toString() + " раунд"
                            }
                            var newTeam: String = teamsNums[count].toString() + " команда"
                            val intent = Intent(this, LastWord::class.java)
                            intent.putExtra("teamsAmount", teamNums)
                            intent.putExtra("currentWord", word.text)
                            intent.putExtra("newRound", newRound)
                            intent.putExtra("newTeam", newTeam)
                            intent.putExtra("settingsText", settingsText)
                            intent.putExtra("settingsInfo", settingsInfo)
                            intent.putExtra("teams", teamsExtra)
                            intent.putExtra("counter", count)
                            intent.putExtra("teamsScores", teamsScores)
                            intent.putExtra("book", wordList)
                            intent.putExtra("currentTeam", roundTitle.text.toString())
                            intent.putExtra("currentRound", roundText.text.toString())
                            for (i in teamsExtra.indices)
                                intent.putExtra("list$i", list[i].toTypedArray())
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                            finish()
                        }
                        lastWord.visibility= View.VISIBLE

                    }




                    elapsedMillis = SystemClock.elapsedRealtime() - chronometer.base
                }
            }

            var file: InputStream = assets.open("Easy.txt")

            var bufferedReader = BufferedReader(InputStreamReader(file))

            word.text = newWord(file, bufferedReader)

            /*
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
            }*/



            check.setOnClickListener {
                check.isClickable = false
                if (flagForFirstTap) {
                    if (flagForLastWord) {
                        if (count == teamNums) {
                            count = 0
                            newRound =
                                (newRound.substringBefore(" ").toInt() + 1).toString() + " раунд"


                            for (i in teamsExtra.indices) {
                                if (teamsScores[i] > max) {
                                    max = teamsScores[i]
                                    winnersIndex = i
                                }
                            }

                        }

                        if (count == 0) {
                            teamsScores[teamNums-1]++
                        } else {
                            teamsScores[count - 1]++
                        }



                        if (max >= settingsText[0]) {
                            val intent = Intent(this, WinPage::class.java)
                            intent.putExtra("WinTeamName", teamsExtra[winnersIndex])
                            intent.putExtra("teams", teamsExtra)
                            intent.putExtra("WinTeamScore", max)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            for (i in teamsExtra.indices)
                                intent.putExtra("list$i", list[i].toTypedArray())
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
                            for (i in teamsExtra.indices)
                                intent.putExtra("list$i", list[i].toTypedArray())
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                            finish()

                        }
                    }
                    knowWordsCounter.text =
                        (knowWordsCounter.text.toString().toInt() + 1).toString()
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
                    list[count][roundText.text.toString().dropLast(6).toInt() - 1] =
                        "${((list[count][roundText.text.toString().dropLast(6)
                            .toInt() - 1].substringBefore('.')
                            .toInt()) + 1)}.${list[count][roundText.text.toString()
                            .dropLast(6).toInt() - 1].substringAfter('.')}"
                }
                check.isClickable = true
            }
        }

        cross.setOnClickListener {
            cross.isClickable = false
            if (flagForFirstTap) {
                if (flagForLastWord) {
                    if (count == teamNums) {
                        count = 0
                        newRound =
                            (newRound.substringBefore(" ")
                                .toInt() + 1).toString() + " раунд"


                        for (i in teamsExtra.indices) {
                            if (teamsScores[i] > max) {
                                max = teamsScores[i]
                                winnersIndex = i
                            }
                        }

                    }

                    if (settingsInfo[0]) {
                        if (count == 0) {
                            teamsScores[teamNums-1]--
                        } else {
                            teamsScores[count - 1]--
                        }
                    }

                    if (max >= settingsText[0]) {
                        val intent = Intent(this, WinPage::class.java)
                        intent.putExtra("WinTeamName", teamsExtra[winnersIndex])
                        intent.putExtra("teams", teamsExtra)
                        intent.putExtra("WinTeamScore", max)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        for (i in teamsExtra.indices)
                            intent.putExtra("list$i", list[i].toTypedArray())
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
                        for (i in teamsExtra.indices)
                            intent.putExtra("list$i", list[i].toTypedArray())
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                        finish()
                    }
                }
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
            list[count][roundText.text.toString().dropLast(6).toInt() - 1] =
                "${((list[count][roundText.text.toString().dropLast(6)
                    .toInt() - 1].substringBefore('.')
                    .toInt()))}.${list[count][roundText.text.toString()
                    .dropLast(6).toInt() - 1].substringAfter('.').toInt() + 1}"
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
    }

    private fun newWord(file: InputStream, bufferedReader: BufferedReader): String {

        currentWordNumber = (0 until wordsNumber).random()
        while (words.contains(currentWordNumber)) {
            if (currentWordNumber == wordsNumber-1) currentWordNumber = 0
            currentWordNumber++
        }
        words.add(currentWordNumber)
        if (words.size == wordsNumber) {
            words.removeAll(words)
            Log.e("Error", "${wordsNumber}")
        }
        for (i in 1 until currentWordNumber) bufferedReader.readLine()
        currentWord = bufferedReader.readLine()
        return currentWord
    }
}