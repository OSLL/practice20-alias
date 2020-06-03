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

        continue_button.setOnClickListener {
            Toast.makeText(this, "Continue", Toast.LENGTH_LONG).show()
        }
        button.setOnClickListener{
            val intent= Intent(this,GameSettings::class.java)
            startActivity(intent)
        }
    }

}
