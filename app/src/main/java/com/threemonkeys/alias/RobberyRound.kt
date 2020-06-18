package com.threemonkeys.alias

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_robbery_round.*
import kotlinx.android.synthetic.main.activity_robbery_round.pauseButton
import kotlinx.android.synthetic.main.activity_round.backButton
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
    private var wordsNumber: Int = 0
    private var tasksNumber: Int = 0
    private var currentWord = ""
    private var currentWordNumber: Int = 0
    private var currentTaskNumber: Int = 0
    private var words: MutableList<Int> = mutableListOf()

    private var flagForAppClosedOrBackButtonPressed:Boolean=true
    var flagForFirstTap: Boolean = false
    var flagForLastWord: Boolean = false
    var flagForPauseCheck: Boolean = true
    private var flagForPause: Boolean = false
    var isPlaying = false

    lateinit var robberyRoundAdapter: RobberyRoundAdapter
    lateinit var teams: Array<String>
    lateinit var teamsScores: Array<Int>
    var book: Int = -1

    lateinit var countDownTimer:CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_robbery_round)

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()
        val teamsAmount = appPrefs.getInt("teamsAmount", 0)

        lastWordRobbery.visibility = View.INVISIBLE

        teams = Array(teamsAmount) { "" }
        teamsScores = Array(teamsAmount) { 0 }
        for (i in teams.indices)
            teams[i] = appPrefs.getString("team$i", "").toString()
        for (i in teams.indices)
            teamsScores[i] = appPrefs.getInt("teamsScores$i", 0)
        var winnersIndex: Int = -1
        var currentRoundText: String = appPrefs.getString("currentRoundText", "1 раунд").toString()
        val currentTeamText: String = appPrefs.getString("currentTeamText", "Error").toString()
        roundTitle.text = currentTeamText
        roundText.text = currentRoundText
        book = appPrefs.getInt("book", -1)
        var counter = appPrefs.getInt("counter", -1)
        var max = -100000
        val tasks = appPrefs.getBoolean("tasks", false)
        val roundLength = appPrefs.getInt("roundLength", 10)
        val wordsForWin = appPrefs.getInt("wordsForWin", 10)
        var roundNumber = appPrefs.getInt("roundNumber", 0)
        timerCounter.text = roundLength.toString()
        var timeLeftMilliseconds: Long = (roundLength.toLong() * 1000)

        class MyCountDownTimer(timeLeftMilliseconds: Long,  interval: Long) :
            CountDownTimer(timeLeftMilliseconds, interval) {

            override fun onTick(p0: Long) {
                timeLeftMilliseconds = p0
                timerCounter.text = (timeLeftMilliseconds / 1000).toString()
            }

            override fun onFinish() {
                timerCounter.text = "0"
                pauseButton.isClickable = false
                Toast.makeText(applicationContext, "Время вышло!", Toast.LENGTH_SHORT)
                    .show()
                counter += 1

                flagForLastWord = true
                lastWordRobbery.visibility = View.VISIBLE
            }

        }

         countDownTimer = MyCountDownTimer(timeLeftMilliseconds, 1000)

        if (counter == 0)
            roundNumber++

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
                    if (flagForFirstTap && flagForPauseCheck) {
                        if (flagForLastWord) {
                            if (counter == teamsAmount) {
                                counter = 0
                                currentRoundText = (currentRoundText.substringBefore(" ").toInt() + 1).toString() + " раунд"

                                for (i in teams.indices) {
                                    if (teamsScores[i] > max) {
                                        max = teamsScores[i]
                                        winnersIndex = i
                                    }
                                }
                            }

                            teamsScores[position]++
                            list[position][roundNumber - 1] = "${((list[position][roundNumber - 1].substringBefore('.').toInt()) + 1)}.${list[position][roundNumber - 1].substringAfter('.')}"

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
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                startActivity(intent)
                                max = 0
                                finish()
                            } else {
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
                                startActivity(intent)
                                overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
                                finish()
                            }
                        }
                        when (book) {
                            0 -> {
                                val file: InputStream = assets.open("Easy.txt")

                                val bufferedReader = BufferedReader(InputStreamReader(file))

                                word.text = newWord(file, bufferedReader)
                            }
                            1 -> {
                                val file: InputStream = assets.open("Middle.txt")

                                val bufferedReader = BufferedReader(InputStreamReader(file))

                                word.text = newWord(file, bufferedReader)
                            }
                            2 -> {
                                val file: InputStream = assets.open("Hard.txt")

                                val bufferedReader = BufferedReader(InputStreamReader(file))

                                word.text = newWord(file, bufferedReader)
                            }
                        }
                        teamsScores[position]++
                        teamsScores1[position]++
                        list[position][roundNumber - 1] = "${((list[position][roundNumber - 1].substringBefore('.').toInt()) + 1)}.${list[position][roundNumber - 1].substringAfter('.')}"
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
                val file: InputStream = assets.open("Easy.txt")

                val bufferedReader = BufferedReader(InputStreamReader(file))

                while (bufferedReader.readLine() != null) wordsNumber++
                file.close()
            }
            1 -> {
                val file: InputStream = assets.open("Middle.txt")

                val bufferedReader = BufferedReader(InputStreamReader(file))

                while (bufferedReader.readLine() != null) wordsNumber++
                file.close()
            }
            2 -> {
                val file: InputStream = assets.open("Hard.txt")

                val bufferedReader = BufferedReader(InputStreamReader(file))

                while (bufferedReader.readLine() != null) wordsNumber++
                file.close()
            }
        }

        pauseButton.setOnClickListener {
            if (flagForPause) {
                if (isPlaying) {
                    pauseButton.background = resources.getDrawable(R.drawable.medium_level_button)
                    flagForPauseCheck = false
                    cross.isClickable = false
                    word.text = "Пауза"
                    countDownTimer.cancel()
                    isPlaying = false
                } else {
                    pauseButton.background = resources.getDrawable(R.drawable.hard_level_button)
                    flagForPauseCheck = true
                    cross.isClickable = true
                    when (book) {
                        0 -> {
                            val file: InputStream = assets.open("Easy.txt")

                            val bufferedReader = BufferedReader(InputStreamReader(file))

                            word.text = newWord(file, bufferedReader)
                        }
                        1 -> {
                            val file: InputStream = assets.open("Middle.txt")

                            val bufferedReader = BufferedReader(InputStreamReader(file))

                            word.text = newWord(file, bufferedReader)
                        }
                        2 -> {
                            val file: InputStream = assets.open("Hard.txt")

                            val bufferedReader = BufferedReader(InputStreamReader(file))

                            word.text = newWord(file, bufferedReader)
                        }
                    }
                    countDownTimer = MyCountDownTimer(timeLeftMilliseconds, 1000)
                    countDownTimer.start()
                    isPlaying = true
                }
            }
        }

        word.setOnClickListener {
            flagForFirstTap = true
            flagForPause = true
            countDownTimer.start()
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

            when (book) {
                0 -> {
                    val file: InputStream = assets.open("Easy.txt")

                    val bufferedReader = BufferedReader(InputStreamReader(file))

                    word.text = newWord(file, bufferedReader)
                }
                1 -> {
                    val file: InputStream = assets.open("Middle.txt")

                    val bufferedReader = BufferedReader(InputStreamReader(file))

                    word.text = newWord(file, bufferedReader)
                }
                2 -> {
                    val file: InputStream = assets.open("Hard.txt")

                    val bufferedReader = BufferedReader(InputStreamReader(file))

                    word.text = newWord(file, bufferedReader)
                }
            }
        }

        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды
        //Реализовать вычет очков для играющей команды

        cross.setOnClickListener {
            cross.isClickable = false
            if (flagForFirstTap) {
                if (flagForLastWord) {
                    if (counter == teamsAmount) {
                        counter = 0
                        currentRoundText = (currentRoundText.substringBefore(" ").toInt() + 1).toString() + " раунд"

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
                        prefsEditor.putInt("max", max)
                        prefsEditor.putString("winner", teams[winnersIndex])
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
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

                when (book) {
                    0 -> {
                        val file: InputStream = assets.open("Easy.txt")

                        val bufferedReader = BufferedReader(InputStreamReader(file))

                        word.text = newWord(file, bufferedReader)
                    }
                    1 -> {
                        val file: InputStream = assets.open("Middle.txt")

                        val bufferedReader = BufferedReader(InputStreamReader(file))

                        word.text = newWord(file, bufferedReader)
                    }
                    2 -> {
                        val file: InputStream = assets.open("Hard.txt")

                        val bufferedReader = BufferedReader(InputStreamReader(file))

                        word.text = newWord(file, bufferedReader)
                    }
                }
            }
            cross.isClickable = true
        }
    }

    override fun finish() {
        flagForAppClosedOrBackButtonPressed=false
        super.finish()
        overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down)
    }
    override fun onStop(){
        super.onStop()
        if (flagForPause) {
            if (isPlaying) {
                pauseButton.background = resources.getDrawable(R.drawable.medium_level_button)
                flagForPauseCheck = false
                cross.isClickable = false
                word.text = "Пауза"
                countDownTimer.cancel()
                isPlaying = false
            }
        }

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
        }

        for (i in 0 until currentWordNumber) bufferedReader.readLine()
        currentWord = bufferedReader.readLine()

        file.close()

        return currentWord
    }
}