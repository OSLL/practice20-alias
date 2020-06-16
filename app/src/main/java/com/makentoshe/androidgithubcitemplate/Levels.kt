package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_levels.*

class Levels : AppCompatActivity() {

    lateinit var list: Array<MutableList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_levels)

        var appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

//        var teams = this.intent.getStringArrayExtra("teams")
//        list = Array(teams.size) { MutableList(0) { "0.0" } }
//
//        for (i in teams.indices)
//            list[i] = this.intent.getStringArrayExtra("list$i").toMutableList()
//
//        var teamsScores: IntArray = IntArray(teams.size) { 0 }
//
//        val intentTeam: String = "1 команда"
//        val intentRound: String = "1 раунд"
//        val additional: Int = 0

        easyLevelIcon.setOnClickListener {
            val intent = Intent(this, Game::class.java)
//            intent.putExtra("teams", teams)
//            intent.putExtra("settingsText", this.intent.getIntArrayExtra("settingsText"))
//            intent.putExtra("settingsInfo", this.intent.getBooleanArrayExtra("settingsInfo"))
//            intent.putExtra("newTeam", intentTeam)
//            intent.putExtra("newRound", intentRound)
//            intent.putExtra("counter", additional)
//            intent.putExtra("teamsScores", teamsScores)
//            intent.putExtra("book", 0)
//            for (i in teams.indices)
//                intent.putExtra("list$i", list[i].toTypedArray())
            prefsEditor.putInt("book", 0)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        middleLevelIcon.setOnClickListener {

            val intent = Intent(this, Game::class.java)
//            intent.putExtra("teams", teams)
//            intent.putExtra("settingsText", this.intent.getIntArrayExtra("settingsText"))
//            intent.putExtra("settingsInfo", this.intent.getBooleanArrayExtra("settingsInfo"))
//            intent.putExtra("newTeam", intentTeam)
//            intent.putExtra("newRound", intentRound)
//            intent.putExtra("counter", additional)
//            intent.putExtra("teamsScores", teamsScores)
//            intent.putExtra("book", 1)
//            for (i in teams.indices)
//                intent.putExtra("list$i", list[i].toTypedArray())
            prefsEditor.putInt("book", 1)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        hardLevelIcon.setOnClickListener {

            val intent = Intent(this, Game::class.java)
//            intent.putExtra("teams", teams)
//            intent.putExtra("settingsText", this.intent.getIntArrayExtra("settingsText"))
//            intent.putExtra("settingsInfo", this.intent.getBooleanArrayExtra("settingsInfo"))
//            intent.putExtra("newTeam", intentTeam)
//            intent.putExtra("newRound", intentRound)
//            intent.putExtra("counter", additional)
//            intent.putExtra("teamsScores", teamsScores)
//            intent.putExtra("book", 2)
//            for (i in teams.indices)
//                intent.putExtra("list$i", list[i].toTypedArray())
            prefsEditor.putInt("book", 2)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        easyLevelText.setOnClickListener {

            val intent = Intent(this, Game::class.java)
//            intent.putExtra("teams", teams)
//            intent.putExtra("settingsText", this.intent.getIntArrayExtra("settingsText"))
//            intent.putExtra("settingsInfo", this.intent.getBooleanArrayExtra("settingsInfo"))
//            intent.putExtra("newTeam", intentTeam)
//            intent.putExtra("newRound", intentRound)
//            intent.putExtra("counter", additional)
//            intent.putExtra("teamsScores", teamsScores)
//            intent.putExtra("book", 0)
//            for (i in teams.indices)
//                intent.putExtra("list$i", list[i].toTypedArray())
            prefsEditor.putInt("book", 0)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        middleLevelText.setOnClickListener {

            val intent = Intent(this, Game::class.java)
//            intent.putExtra("teams", teams)
//            intent.putExtra("settingsText", this.intent.getIntArrayExtra("settingsText"))
//            intent.putExtra("settingsInfo", this.intent.getBooleanArrayExtra("settingsInfo"))
//            intent.putExtra("newTeam", intentTeam)
//            intent.putExtra("newRound", intentRound)
//            intent.putExtra("counter", additional)
//            intent.putExtra("teamsScores", teamsScores)
//            intent.putExtra("book", 1)
//            for (i in teams.indices)
//                intent.putExtra("list$i", list[i].toTypedArray())
            prefsEditor.putInt("book", 1)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        hardLevelText.setOnClickListener {

            val intent = Intent(this, Game::class.java)
//            intent.putExtra("teams", teams)
//            intent.putExtra("settingsText", this.intent.getIntArrayExtra("settingsText"))
//            intent.putExtra("settingsInfo", this.intent.getBooleanArrayExtra("settingsInfo"))
//            intent.putExtra("newTeam", intentTeam)
//            intent.putExtra("newRound", intentRound)
//            intent.putExtra("counter", additional)
//            intent.putExtra("teamsScores", teamsScores)
//            intent.putExtra("book", 2)
//            for (i in teams.indices)
//                intent.putExtra("list$i", list[i].toTypedArray())
            prefsEditor.putInt("book", 2)
            prefsEditor.apply()
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        backButton.setOnClickListener {
            val intent = Intent(this, GameSettings::class.java)
//            intent.putExtra("teams", teams)
//            intent.putExtra("settingsText", this.intent.getIntArrayExtra("settingsText"))
//            intent.putExtra("settingsInfo", this.intent.getBooleanArrayExtra("settingsInfo"))
//            for (i in teams.indices)
//                intent.putExtra("list$i", list[i].toTypedArray())
            startActivity(intent)
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}