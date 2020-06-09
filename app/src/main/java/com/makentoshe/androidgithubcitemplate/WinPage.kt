package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_win_page.*

class WinPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win_page)

        var winner:String=this.intent.getStringExtra("WinTeamName")
        var max:Int = this.intent.getIntExtra("WinTeamScore",-1)
        if ((max - (max % 10)) / 10 == 1) {
            youWin.text="Команда: $winner победила набрав $max очков"
        } else {
            when (max % 10) {
                0 -> youWin.text="Команда: $winner победила набрав $max очков"
                1 ->  youWin.text="Команда: $winner победила набрав $max очко"
                2, 3, 4 ->  youWin.text="Команда: $winner победила набрав $max очка"
                5, 6, 7, 8, 9 ->  youWin.text="Команда: $winner победила набрав $max очков"
            }
        }
        backToMenu.setOnClickListener{
            finish()
        }
    }
    override fun finish() {
        super.finish()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}