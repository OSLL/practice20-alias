package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game_settings.*

class GameSettings : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_settings)



        var teams = this.intent.getStringArrayExtra("teams")
        var settingsText = this.intent.getIntArrayExtra("settingsText")
        var settingsInfo =this.intent.getBooleanArrayExtra("settingsInfo" )

        fineSwitcher.isChecked = settingsInfo[0]

        fineSwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) settingsInfo[0]=true else settingsInfo[0]=false}

        generalLastWordSwitcher.isChecked = settingsInfo[1]

        generalLastWordSwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) settingsInfo[1]=true else settingsInfo[1]=false}

        taskSwitcher.isChecked = settingsInfo[2]

        taskSwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) settingsInfo[2]=true else settingsInfo[2]=false}

        robberySwitcher.isChecked = settingsInfo[3]

        robberySwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) settingsInfo[3]=true else settingsInfo[3]=false}


        continueButton.setOnClickListener {

            val intent = Intent(this, Levels::class.java)
            intent.putExtra("teams", teams)
            intent.putExtra("settingsText",settingsText)
            intent.putExtra("settingsInfo", settingsInfo)
            startActivity(intent)

            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }



        wordsAmountSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                wordsAmountCounter.text = "$p1"
                settingsText[0] = wordsAmountCounter.text.toString().toInt()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        wordsAmountSlider.progress=settingsText[0]

        roundTimeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                roundTimeCounter.text = "$p1"
                settingsText[1] = roundTimeCounter.text.toString().toInt()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        roundTimeSlider.progress=settingsText[1]

        backButton.setOnClickListener {
            val intent = Intent(this, Teams::class.java)
            intent.putExtra("teams", teams)
            intent.putExtra("settingsText",settingsText)
            intent.putExtra("settingsInfo", settingsInfo)
            startActivity(intent)
            finish()
        }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {}


}