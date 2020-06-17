package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {

    lateinit var list: Array<MutableList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

        val teamsExtra = this.intent.getStringArrayExtra("teams")
        var roundNumber = appPrefs.getInt("roundNumber", 0)

        list = Array(teamsExtra.size) { MutableList(roundNumber) { "0.0" } }
        for (i in teamsExtra.indices)
            for (j in 0 until roundNumber)
                list[i][j] = appPrefs.getString("list[$i][$j]", "0.0").toString()

        backButton.setOnClickListener {
            finish()
        }

        toMenuButton.setOnClickListener {
            finish()
        }

        var firstAdapter = FirstAdapter(this, list, teamsExtra.toMutableList())
        firstView.adapter = firstAdapter
        firstView.layoutManager = LinearLayoutManager(this)
    }

    override fun finish() {
        super.finish()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}