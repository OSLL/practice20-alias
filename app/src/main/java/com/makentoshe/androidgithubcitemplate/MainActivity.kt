package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list: Array<MutableList<String>> = Array(0) { MutableList(0) { "0.0" } }

        continueGameButton.setOnClickListener {
            Toast.makeText(this, "Continue", Toast.LENGTH_SHORT).show()
            var intent = Intent(this, Statistics::class.java)
            startActivity(intent)
        }


        newGameButton.setOnClickListener {
            var teams: MutableList<String> = mutableListOf()
            var settingsText: IntArray = arrayOf(10, 10).toIntArray()
            var settingsInfo: BooleanArray = Array<Boolean>(4, { false }).toBooleanArray()
            val intent = Intent(this, Teams::class.java)
            intent.putExtra("teams", teams.toTypedArray())
            intent.putExtra("settingsText", settingsText)
            intent.putExtra("settingsInfo", settingsInfo)
            for (i in teams.indices)
                intent.putExtra("list$i", list[i].toTypedArray())
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }

        rules_button.setOnClickListener {
            val intent = Intent(this, Rules::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_up)
        }
    }
}
