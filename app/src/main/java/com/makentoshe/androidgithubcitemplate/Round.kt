package com.makentoshe.androidgithubcitemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_round.*

class Round : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_round)

        backButton.setOnClickListener {
            finish()
        }

        check.setOnClickListener{
            knowWordsCounter.setText((knowWordsCounter.text.toString().toInt() + 1).toString())
        }

        cross.setOnClickListener{
            skipWordsCounter.setText((skipWordsCounter.text.toString().toInt() + 1).toString())
        }
    }



    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
    }
}