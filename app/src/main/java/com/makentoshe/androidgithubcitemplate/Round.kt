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

//    lateinit var list: Array<MutableList<String>>

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

        var teamsAmount: Int = appPrefs.getInt("teamsAmount", 0)
        teams = Array(teamsAmount) { "" }
        var currentRoundText: String = appPrefs.getString("currentRoundText", "1 раунд").toString()
        var currentTeamText: String = appPrefs.getString("currentTeamText", "1 команда").toString()
        for (i in 0 until teamsAmount)
            teams[i] = appPrefs.getString("team$i", "").toString()
        var book = appPrefs.getInt("book", -1)
        teamsScores = Array(teamsAmount) { 0 }
        for (i in 0 until teamsAmount)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)
        var roundLength = appPrefs.getInt("roundLength", 10)
        var wordsForWin = appPrefs.getInt("wordsForWin", 10)
        var fineChanger = appPrefs.getBoolean("fineChanger", false)
        var generalLast = appPrefs.getBoolean("generalLast", false)
        var tasks = appPrefs.getBoolean("tasks", false)
        var isPlaying = false
        var winnersIndex: Int = -1
        var counter = appPrefs.getInt("counter", -1)
        var timeLeftMilliseconds:Long=(roundLength.toLong()*1000)

        class myCountDownTimer(timeLeftMilliseconds:Long,val interval:Long): CountDownTimer(timeLeftMilliseconds, interval){

            override fun onTick(p0: Long) {
                timeLeftMilliseconds=p0
                timerCounter.text=(timeLeftMilliseconds/1000).toString()
                if (timerCounter.text.toString().toInt() <= 0) {
                    chronometer.stop()
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
                        for (i in 0 until teamsAmount)
                        prefsEditor.putInt("teamsScores$i", teamsScores[i])
                        prefsEditor.putInt("counter", counter)
                        prefsEditor.putString("currentRoundText", currentRoundText)
                        prefsEditor.apply()
                        val intent = Intent(this@Round, LastWord::class.java)
//                            intent.putExtra("teamsAmount", teamsAmount)
//                            intent.putExtra("currentWord", word.text)
//                            intent.putExtra("currentRoundText", currentRoundText)
//                            intent.putExtra("newTeam", newTeam)
//                            intent.putExtra("teams", teams)
//                            intent.putExtra("counter", count)
//                            intent.putExtra("teamsScores", teamsScores)
//                            intent.putExtra("book", book)
//                            intent.putExtra("currentTeam", roundTitle.text.toString())
//                            intent.putExtra("currentRound", roundText.text.toString())
//                            for (i in teams.indices)
//                                intent.putExtra("list$i", list[i].toTypedArray())
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                        finish()
                    }
                    lastWord.visibility = View.VISIBLE

                }
            }

            override fun onFinish() {

            }

        }
        var countDownTimer: myCountDownTimer =  myCountDownTimer(timeLeftMilliseconds, 1000)

//        list = Array(teams.size) { MutableList(0) { "0.0" } }
//        for (i in teams.indices)
//            list[i] = this.intent.getStringArrayExtra("list$i")!!.toMutableList()

        roundTitle.text = currentTeamText
        roundText.text = currentRoundText

        val teamsNums = Array(teamsAmount) { it + 1 }

//        if (counter == 0)
//            for (i in list) i.add("0.0")

        timerCounter.text = roundLength.toString()
        Log.e("Sassasasa", timerCounter.text.toString())

        backButton.setOnClickListener {
            finish()
        }

        if (!tasks)
            taskText.visibility = View.GONE

        when (book) {
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
    //    var pauseOffSet: Long = 0




        pauseButton.setOnClickListener {
            if (flagForPause) {
                if (isPlaying) {
                    pauseButton.background = resources.getDrawable(R.drawable.medium_level_button)
                    check.isClickable = false
                    cross.isClickable = false
                    word.text = "Пауза"

                    countDownTimer.cancel()
                  //  chronometer.stop()
                  //  pauseOffSet = SystemClock.elapsedRealtime() - chronometer.base
                    isPlaying = false
                } else {
                    pauseButton.background = resources.getDrawable(R.drawable.hard_level_button)
                    check.isClickable = true
                    cross.isClickable = true
                    when (book) {
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
                    countDownTimer =  myCountDownTimer(timeLeftMilliseconds, 1000)
                    countDownTimer.start()
                 //   chronometer.base = SystemClock.elapsedRealtime() - pauseOffSet
                 //   chronometer.start()
                 /*   timerCounter.text = (chronometer.text.toString().substringBefore(':')
                        .toInt() * 60 + chronometer.text.toString().substringAfter(':')
                        .toInt()).toString()*/
                    isPlaying = true
                }
            }
        }

        word.setOnClickListener {
            flagForFirstTap = true
            flagForPause = true
            countDownTimer.start()
            //chronometer.base = SystemClock.elapsedRealtime() + (timerCounter.text.toString()
           //     .toInt() * 1000).toLong() + 500
           // chronometer.start()

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

//            chronometer.setOnChronometerTickListener {
//
//                timerCounter.text = (chronometer.text.toString().substringBefore(':')
//                    .toInt() * 60 + chronometer.text.toString().substringAfter(':')
//                    .toInt()).toString()//(timerCounter.text.toString().toInt() - 1).toString()
//                if (timerCounter.text.toString().toInt() <= 0) {
//                    chronometer.stop()
//                    pauseButton.isClickable = false
//                    Toast.makeText(applicationContext, "Время вышло!", Toast.LENGTH_SHORT)
//                        .show()
//                    counter += 1
//
//                    flagForLastWord = true
//                    if (generalLast) {
//                        if (counter == teamsAmount) {
//                            counter = 0
//                            currentRoundText =
//                                (currentRoundText.substringBefore(" ")
//                                    .toInt() + 1).toString() + " раунд"
//                        }
//                        prefsEditor.putString("currentWord", word.text.toString())
//                        prefsEditor.apply()
//                        val intent = Intent(this, LastWord::class.java)
//                            intent.putExtra("teamsAmount", teamsAmount)
//                            intent.putExtra("currentWord", word.text)
//                            intent.putExtra("currentRoundText", currentRoundText)
//                            intent.putExtra("newTeam", newTeam)
//                            intent.putExtra("teams", teams)
//                            intent.putExtra("counter", count)
//                            intent.putExtra("teamsScores", teamsScores)
//                            intent.putExtra("book", book)
//                            intent.putExtra("currentTeam", roundTitle.text.toString())
//                            intent.putExtra("currentRound", roundText.text.toString())
//                            for (i in teams.indices)
//                                intent.putExtra("list$i", list[i].toTypedArray())
//                        startActivity(intent)
//                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
//                        finish()
//                    }
//                    lastWord.visibility = View.VISIBLE
//
//                }
//            }

            when (book) {
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

            check.setOnClickListener {
                check.isClickable = false
                if (flagForFirstTap) {
                    if (flagForLastWord) {
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

                        if (counter == 0) {
                            teamsScores[teamsAmount - 1]++
                        } else {
                            teamsScores[counter - 1]++
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
//                            intent.putExtra("WinTeamName", teams[winnersIndex])
//                            intent.putExtra("teams", teams)
//                            intent.putExtra("WinTeamScore", max)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                            for (i in teams.indices)
//                                intent.putExtra("list$i", list[i].toTypedArray())
                            startActivity(intent)
                            max = 0
                            finish()
                        } else {
                            val intent = Intent(this, Game::class.java)
                            for (i in 0 until teamsAmount)
                                prefsEditor.putInt("teamsScores$i", teamsScores[i])
                            prefsEditor.putInt("counter", counter)
                            prefsEditor.putString("currentRoundText", currentRoundText)
                            prefsEditor.apply()
//                            intent.putExtra("currentRoundText", currentRoundText)
//                            intent.putExtra("newTeam", newTeam)
//                            intent.putExtra("settingsText", settingsText)
//                            intent.putExtra("settingsInfo", settingsInfo)
//                            intent.putExtra("teams", teams)
//                            intent.putExtra("counter", count)
//                            intent.putExtra("teamsScores", teamsScores)
//                            intent.putExtra("book", book)
//                            for (i in teams.indices)
//                                intent.putExtra("list$i", list[i].toTypedArray())
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                            finish()

                        }
                    }
                    knowWordsCounter.text =
                        (knowWordsCounter.text.toString().toInt() + 1).toString()
                    when (book) {
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
                    teamsScores[counter]++
//                    list[count][roundText.text.toString().dropLast(6).toInt() - 1] =
//                        "${((list[count][roundText.text.toString().dropLast(6)
//                            .toInt() - 1].substringBefore('.')
//                            .toInt()) + 1)}.${list[count][roundText.text.toString()
//                            .dropLast(6).toInt() - 1].substringAfter('.')}"
                }
                check.isClickable = true
            }
        }

        cross.setOnClickListener {
            cross.isClickable = false
            if (flagForFirstTap) {
                if (flagForLastWord) {
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

                    if (fineChanger) {
                        if (counter == 0) {
                            teamsScores[teamsAmount - 1]--
                        } else {
                            teamsScores[counter - 1]--
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
//                        intent.putExtra("WinTeamName", teams[winnersIndex])
//                        intent.putExtra("teams", teams)
//                        intent.putExtra("WinTeamScore", max)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                        for (i in teams.indices)
//                            intent.putExtra("list$i", list[i].toTypedArray())
                        startActivity(intent)
                        max = 0
                        finish()
                    } else {
                        var newTeam: String = teamsNums[counter].toString() + " команда"
                        val intent = Intent(this, Game::class.java)
                        for (i in 0 until teamsAmount)
                            prefsEditor.putInt("teamsScores$i", teamsScores[i])
                        prefsEditor.putInt("counter", counter)
                        prefsEditor.putString("currentRoundText", currentRoundText)
                        prefsEditor.apply()
//                        intent.putExtra("currentRoundText", currentRoundText)
//                        intent.putExtra("newTeam", newTeam)
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
                skipWordsCounter.text = (skipWordsCounter.text.toString().toInt() + 1).toString()
                when (book) {
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
                if (fineChanger) {
                    teamsScores[counter]--
                }
            }
            cross.isClickable = true
//            list[count][roundText.text.toString().dropLast(6).toInt() - 1] =
//                "${((list[count][roundText.text.toString().dropLast(6)
//                    .toInt() - 1].substringBefore('.')
//                    .toInt()))}.${list[count][roundText.text.toString()
//                    .dropLast(6).toInt() - 1].substringAfter('.').toInt() + 1}"
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
    }

    private fun newWord(file: InputStream, bufferedReader: BufferedReader): String {

        currentWordNumber = (0 until wordsNumber).random()
        while (words.contains(currentWordNumber)) {
            if (currentWordNumber == wordsNumber - 1) currentWordNumber = 0
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