package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_game_settings.*

class GameSettings : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    lateinit var list: Array<MutableList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_settings)

        val appPrefs: SharedPreferences = getSharedPreferences("AppNightMode", 0)
        var isNightModeOn = appPrefs.getBoolean("NightMode", false)
        var prefsEditor: SharedPreferences.Editor = appPrefs.edit()


        if (isNightModeOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        settings.setOnClickListener {
            isNightModeOn = !isNightModeOn
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                prefsEditor.putBoolean("NightMode", isNightModeOn)
                prefsEditor.apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                prefsEditor.putBoolean("NightMode", isNightModeOn)
                prefsEditor.apply()
            }
        }

        var teams = this.intent.getStringArrayExtra("teams")
        list = Array(teams.size) { MutableList(0) { "0.0" } }
        var settingsText = this.intent.getIntArrayExtra("settingsText")
        var settingsInfo =this.intent.getBooleanArrayExtra("settingsInfo" )

        for (i in teams.indices)
            list[i] = this.intent.getStringArrayExtra("list$i").toMutableList()

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
            for (i in teams.indices)
                intent.putExtra("list$i", list[i].toTypedArray())
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
            for (i in teams.indices)
                intent.putExtra("list$i", list[i].toTypedArray())
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