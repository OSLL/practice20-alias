package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_robbery_round.*
import kotlinx.android.synthetic.main.activity_robbery_round.pauseButton
import kotlinx.android.synthetic.main.activity_round.*
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

    lateinit var list: Array<MutableList<String>>

    var wordsNumber: Int = 0
    var tasksNumber: Int = 0

    var currentWord = ""

    var currentWordNumber: Int = 0
    var currentTaskNumber: Int = 0

    var words: MutableList<Int> = mutableListOf()

    var flagForFirstTap: Boolean = false
    var flagForLastWord: Boolean = false
    var flagForPauseCheck:Boolean = true

    lateinit var robberyRoundAdapter: RobberyRoundAdapter

    lateinit var teams: Array<String>
    lateinit var teamsScores: Array<Int>
    var book: Int = -1

    var flagForPause: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robbery_round)

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()
        val teamsAmount = appPrefs.getInt("teamsAmount", 0)

        lastWordRobbery.visibility = View.INVISIBLE

        teams = Array(teamsAmount) { "" }
        teamsScores = Array(teamsAmount) {0}
        for (i in teams.indices)
            teams[i] = appPrefs.getString("team$i", "").toString()
        for (i in teams.indices)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)

        Log.e("Lol", "${teams[1]} ${teamsScores[1]}")
        var winnersIndex: Int = -1
        var currentRoundText: String = appPrefs.getString("currentRoundText", "1 раунд").toString()
        var currrentTeamText: String = appPrefs.getString("currentTeamText", "Error").toString()
        roundTitle.text = currrentTeamText
        roundText.text = currentRoundText
        book = appPrefs.getInt("book", -1)
        val teamsNums = Array(teamsAmount) { it + 1 }
        var counter = appPrefs.getInt("counter", -1)
        var max = -100000
        var isPlaying = false
      //  val fineChanger = appPrefs.getBoolean("fineChanger", false)
    //    val generalLast = appPrefs.getBoolean("generalLast", false)
        val tasks = appPrefs.getBoolean("tasks", false)
        val roundLength = appPrefs.getInt("roundLength", 10)
        val wordsForWin = appPrefs.getInt("wordsForWin", 10)
        var roundNumber = appPrefs.getInt("roundNumber", 0)

        timerCounter.text = roundLength.toString()
        chronometer.base = (timerCounter.text.toString().toInt() * 1000).toLong()

//        list = Array(teams.size) { MutableList(0) { "0.0" } }
//        for (i in teams.indices)
//            list[i] = this.intent.getStringArrayExtra("list$i")!!.toMutableList()
//
        if (counter == 0)
            roundNumber++

        list = Array(teams.size) { MutableList(roundNumber) { "0.0" } }

        for (i in 0 until teamsAmount)
            for (j in 0 until roundNumber)
                list[i][j] = appPrefs.getString("list[$i][$j]", "0.0").toString()

        fun createRecyclerView() {
            var teamsScores1: IntArray = IntArray(teams.size) { 0 }
            robberyRoundAdapter = RobberyRoundAdapter(this, teams, teamsScores1)
            robberyRoundRecycler.adapter = robberyRoundAdapter
            robberyRoundRecycler.layoutManager = LinearLayoutManager(this)

            robberyRoundAdapter.setOnItemClickListener(object :
                RobberyRoundAdapter.onItemClickListener {

                override fun onCheckClicked(position: Int) {
                    if (flagForFirstTap && flagForPauseCheck) {
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



                            if (max >= wordsForWin.toString().toInt()) {
                                val intent = Intent(this@RobberyRound, WinPage::class.java)
                                prefsEditor.putInt("roundNumber", roundNumber)
                                prefsEditor.putInt("max", max)
                                prefsEditor.putString("winner", teams[winnersIndex])
                                for (i in 0 until teamsAmount)
                                    prefsEditor.putInt("teamsScores$i", teamsScores[i])
                                prefsEditor.putInt("counter", counter)
                                prefsEditor.putString("currentRoundText", currentRoundText)
                                prefsEditor.putInt("max", max)
                                prefsEditor.putString("winner", teams[winnersIndex])
                                for (i in 0 until teamsAmount)
                                    for (j in 0 until roundNumber)
                                        prefsEditor.putString("list[$i][$j]", list[i][j])
                                prefsEditor.apply()
//                                intent.putExtra("WinTeamName", teams[winnersIndex])
//                                intent.putExtra("WinTeamScore", max)
//                                intent.putExtra("teams", teams)
//                                for (i in teams.indices)
//                                    intent.putExtra("list$i", list[i].toTypedArray())
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                                max = 0
                                finish()
                            } else {
                                var newTeam: String = teamsNums[counter].toString() + " команда"
                                val intent = Intent(this@RobberyRound, Game::class.java)
                                prefsEditor.putInt("roundNumber", roundNumber)
                                for (i in 0 until teamsAmount)
                                    prefsEditor.putInt("teamsScores$i", teamsScores[i])
                                prefsEditor.putInt("counter", counter)
                                prefsEditor.putString("currentRoundText", currentRoundText)
                                for (i in 0 until teamsAmount)
                                    for (j in 0 until roundNumber)
                                        prefsEditor.putString("list[$i][$j]", list[i][j])
                                prefsEditor.apply()
//                                intent.putExtra("currentRoundText", currentRoundText)
//                                intent.putExtra("newTeam", newTeam)
//                                intent.putExtra("teams", teams)
//                                intent.putExtra("counter", count)
//                                intent.putExtra("teamsScores", teamsScores)
//                                intent.putExtra("book", book)
//                                for (i in teams.indices)
//                                    intent.putExtra("list$i", list[i].toTypedArray())
                                startActivity(intent)
                                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                                finish()

                            }

                        }
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
                        teamsScores[position]++
                        teamsScores1[position]++
                        list[position][roundText.text.toString().dropLast(6).toInt() - 1] =
                            "${((list[position][roundText.text.toString().dropLast(6)
                                .toInt() - 1].substringBefore('.')
                                .toInt()) + 1)}.${list[position][roundText.text.toString()
                                .dropLast(6).toInt() - 1].substringAfter('.')}"
                        robberyRoundAdapter.notifyItemChanged(position)
                    }
                }

            })

        }
        createRecyclerView()







        if (!tasks)
            taskRobberyText.visibility = View.GONE

        backButton.setOnClickListener {
            finish()
        }
        when (book) {
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

        var pauseOffSet:Long=0

        pauseButton.setOnClickListener {
            if (flagForPause) {
                if (isPlaying) {
                    pauseButton.background = resources.getDrawable(R.drawable.medium_level_button)
                  //  check.isClickable = false
                    cross.isClickable = false
                    flagForPauseCheck=false
                    word.text = "Пауза"
                    chronometer.stop()
                    pauseOffSet=SystemClock.elapsedRealtime()-chronometer.base
                    isPlaying = false
                } else {
                    pauseButton.background = resources.getDrawable(R.drawable.hard_level_button)
                  //  check.isClickable = true
                    cross.isClickable = true
                    flagForPauseCheck=true
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
                    chronometer.base=SystemClock.elapsedRealtime()-pauseOffSet
                    chronometer.start()
                    isPlaying = true
                }
            }
        }

        word.setOnClickListener {
            flagForFirstTap = true
            flagForPause = true
            chronometer.base = SystemClock.elapsedRealtime()+(timerCounter.text.toString().toInt() * 1000).toLong()+500
            chronometer.start()
            word.isClickable = false
            isPlaying = true
            if (tasks) {
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
                timerCounter.text =  (chronometer.text.toString().substringBefore(':').toInt()*60+chronometer.text.toString().substringAfter(':').toInt()).toString()//(timerCounter.text.toString().toInt() - 1).toString()
                    if (timerCounter.text.toString().toInt() <= 0) {
                        chronometer.stop()
                        pauseButton.isClickable=false
                        Toast.makeText(applicationContext, "Время вышло!", Toast.LENGTH_SHORT)
                            .show()
                        counter += 1
                        flagForLastWord = true
                        lastWordRobbery.visibility = View.VISIBLE
                    }
            }

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

                    if (max >= wordsForWin.toString().toInt()) {
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
//                        intent.putExtra("WinTeamName", teams[winnersIndex])
//                        intent.putExtra("WinTeamScore", max)
//                        intent.putExtra("teams", teams)
//                        for (i in teams.indices)
//                            intent.putExtra("list$i", list[i].toTypedArray())
                        prefsEditor.putInt("max", max)
                        prefsEditor.putString("winner", teams[winnersIndex])
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        max = 0
                        finish()
                    } else {
                        var newTeam: String = teamsNums[counter].toString() + " команда"
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
//                        intent.putExtra("currentRoundText", currentRoundText)
//                        intent.putExtra("newTeam", newTeam)
//                        intent.putExtra("teams", teams)
//                        intent.putExtra("counter", count)
//                        intent.putExtra("teamsScores", teamsScores)
//                        intent.putExtra("book", book)
//                        for (i in teams.indices)
//                            intent.putExtra("list$i", list[i].toTypedArray())
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                        finish()
                    }
                }
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