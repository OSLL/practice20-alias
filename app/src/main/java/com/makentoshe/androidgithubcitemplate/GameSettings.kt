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

        var wordsForWin = appPrefs.getString("wordsForWin", "10")
        var roundLength = appPrefs.getString("roundLength", "10")
        var fineChecker = appPrefs.getBoolean("fineChecker", false)
        var generalLast = appPrefs.getBoolean("generalLast", false)
        var tasks = appPrefs.getBoolean("tasks", false)
        var robbery = appPrefs.getBoolean("robbery", false)

        fineSwitcher.isChecked = fineChecker

        fineSwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) fineChecker=true else fineChecker=false}

        generalLastWordSwitcher.isChecked = generalLast

        generalLastWordSwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) generalLast=true else generalLast=false}

        taskSwitcher.isChecked = tasks

        taskSwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) tasks=true else tasks=false}

        robberySwitcher.isChecked = robbery

        robberySwitcher.setOnCheckedChangeListener { buttonView, isChecked -> if (isChecked) robbery=true else robbery=false}


        continueButton.setOnClickListener {
            val intent = Intent(this, Levels::class.java)
            prefsEditor.putString("roundLength", roundLength)
            prefsEditor.putString("wordsForWin", wordsForWin)
            prefsEditor.putBoolean("fineChecker", fineChecker)
            prefsEditor.putBoolean("generalLast", generalLast)
            prefsEditor.putBoolean("tasks", tasks)
            prefsEditor.putBoolean("robbery", robbery)
            prefsEditor.apply()
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }



        wordsAmountSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                wordsAmountCounter.text = "$p1"
                wordsForWin = wordsAmountCounter.text.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        wordsAmountSlider.progress= wordsForWin!!.toInt()

        roundTimeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                roundTimeCounter.text = "$p1"
                roundLength = roundTimeCounter.text.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        } )

        roundTimeSlider.progress= roundLength!!.toInt()

        backButton.setOnClickListener {
            val intent = Intent(this, Teams::class.java)
            prefsEditor.putString("roundLength", roundLength)
            prefsEditor.putString("wordsForWin", wordsForWin)
            prefsEditor.putBoolean("fineChecker", fineChecker)
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