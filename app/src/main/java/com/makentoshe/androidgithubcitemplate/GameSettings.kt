package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game_settings.*

class GameSettings : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    lateinit var list: Array<MutableList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_settings)

        var appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

        var wordsForWin = appPrefs.getInt("wordsForWin", 10)
        var roundLength = appPrefs.getInt("roundLength", 10)
        var fineChanger = appPrefs.getBoolean("fineChanger", false)
        var generalLast = appPrefs.getBoolean("generalLast", false)
        var tasks = appPrefs.getBoolean("tasks", false)
        var robbery = appPrefs.getBoolean("robbery", false)

//        var teams = this.intent.getStringArrayExtra("teams")
//        list = Array(teams.size) { MutableList(0) { "0.0" } }
//        var settingsText = this.intent.getIntArrayExtra("settingsText")
//        var settingsInfo =this.intent.getBooleanArrayExtra("settingsInfo" )
//
//        for (i in teams.indices)
//            list[i] = this.intent.getStringArrayExtra("list$i").toMutableList()

        fineSwitcher.isChecked = fineChanger

        fineSwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) fineChanger=true else fineChanger=false}

        generalLastWordSwitcher.isChecked = generalLast

        generalLastWordSwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) generalLast=true else generalLast=false}

        taskSwitcher.isChecked = tasks

        taskSwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) tasks=true else tasks=false}

        robberySwitcher.isChecked = robbery

        robberySwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) robbery=true else robbery=false}


        continueButton.setOnClickListener {

            val intent = Intent(this, Levels::class.java)
//            intent.putExtra("teams", teams)
//            intent.putExtra("settingsText",settingsTetx.toTypedArray())
            prefsEditor.putInt("wordsForWin", wordsForWin)
            prefsEditor.putInt("roundLength", roundLength)
            prefsEditor.putBoolean("fineChanger", fineChanger)
            prefsEditor.putBoolean("generalLast", generalLast)
            prefsEditor.putBoolean("tasks", tasks)
            prefsEditor.putBoolean("robbery", robbery)
            prefsEditor.apply()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        wordsAmountSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                wordsAmountCounter.text = "$p1"
                wordsForWin = wordsAmountCounter.text.toString().toInt()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        wordsAmountSlider.progress=wordsForWin

        roundTimeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                roundTimeCounter.text = "$p1"
                roundLength = roundTimeCounter.text.toString().toInt()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        roundTimeSlider.progress=roundLength

        backButton.setOnClickListener {
            val intent = Intent(this, Teams::class.java)
//            intent.putExtra("teams", teams)
//            for (i in teams.indices)
//                intent.putExtra("list$i", list[i].toTypedArray())
            prefsEditor.putInt("wordsForWin", wordsForWin)
            prefsEditor.putInt("roundLength", roundLength)
            prefsEditor.putBoolean("fineChanger", fineChanger)
            prefsEditor.putBoolean("generalLast", generalLast)
            prefsEditor.putBoolean("tasks", tasks)
            prefsEditor.putBoolean("robbery", robbery)
            prefsEditor.apply()
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