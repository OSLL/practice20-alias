package com.threemonkeys.alias

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_game_settings.*

class GameSettings : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    lateinit var list: Array<MutableList<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_settings)

        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

        var isNightModeOn = appPrefs.getBoolean("isNightModeOn", false)

        if (isNightModeOn) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        settings.setOnClickListener {
            isNightModeOn = !isNightModeOn
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            prefsEditor.putBoolean("isNightModeOn", isNightModeOn)
            prefsEditor.apply()
        }

        var wordsForWin = appPrefs.getInt("wordsForWin", 10)
        var roundLength = appPrefs.getInt("roundLength", 10)
        var fineChanger = appPrefs.getBoolean("fineChanger", false)
        var generalLast = appPrefs.getBoolean("generalLast", false)
        var tasks = appPrefs.getBoolean("tasks", false)
        var robbery = appPrefs.getBoolean("robbery", false)

        fineSwitcher.isChecked = fineChanger

        fineSwitcher.setOnCheckedChangeListener { buttonView, isChecked ->
            fineChanger = isChecked
            prefsEditor.putBoolean("fineChanger", fineChanger)
            prefsEditor.apply()
        }

        generalLastWordSwitcher.isChecked = generalLast

        generalLastWordSwitcher.setOnCheckedChangeListener { buttonView, isChecked ->
            generalLast = isChecked
            prefsEditor.putBoolean("generalLast", generalLast)
            prefsEditor.apply()
        }

        taskSwitcher.isChecked = tasks

        taskSwitcher.setOnCheckedChangeListener { buttonView, isChecked ->
            tasks = isChecked
            prefsEditor.putBoolean("tasks", tasks)
            prefsEditor.apply()
        }

        robberySwitcher.isChecked = robbery

        robberySwitcher.setOnCheckedChangeListener { buttonView, isChecked ->
            robbery = isChecked
            prefsEditor.putBoolean("robbery", robbery)
            prefsEditor.apply()
        }


        continueButton.setOnClickListener {
            prefsEditor.putBoolean("levelsFlag", true)
            val intent = Intent(this, Levels::class.java)
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

        wordsAmountSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                wordsAmountCounter.text = "$p1"
                wordsForWin = wordsAmountCounter.text.toString().toInt()
                prefsEditor.putInt("wordsForWin", wordsForWin)
                prefsEditor.apply()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        wordsAmountSlider.progress = wordsForWin

        roundTimeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                roundTimeCounter.text = "$p1"
                roundLength = roundTimeCounter.text.toString().toInt()
                prefsEditor.putInt("roundLength", roundLength)
                prefsEditor.apply()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}
        })

        roundTimeSlider.progress = roundLength

        backButton.setOnClickListener {
            val intent = Intent(this, Teams::class.java)
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

        fineButton.setOnClickListener {
            val gameSettingsDialog = Dialog(this)
            gameSettingsDialog.setContentView(R.layout.dialog_game_settings)
            val closeGameSettingsDialog: Button =
                gameSettingsDialog.findViewById(R.id.closeGameSettingsDialog)
            val textGameSettingsDialog: TextView =
                gameSettingsDialog.findViewById(R.id.textGameSettingsDialog)
            textGameSettingsDialog.text =
                "Если вы не можете объяснить слово и пропускаете его, то ваша команда теряет одно очко \n P.S. Не распространяется на общее последнее слово"
            gameSettingsDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            gameSettingsDialog.show()
            closeGameSettingsDialog.setOnClickListener {
                gameSettingsDialog.cancel()
            }
        }

        taskButton.setOnClickListener {
            val gameSettingsDialog = Dialog(this)
            gameSettingsDialog.setContentView(R.layout.dialog_game_settings)
            val closeGameSettingsDialog: Button =
                gameSettingsDialog.findViewById(R.id.closeGameSettingsDialog)
            val textGameSettingsDialog: TextView =
                gameSettingsDialog.findViewById(R.id.textGameSettingsDialog)
            textGameSettingsDialog.text = "Задание на раунд будет появляться под карточкой со словом и представлять из себя правило, в соответствии с которым ведущий должен будет объяснить текущее слово"
            gameSettingsDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            gameSettingsDialog.show()
            closeGameSettingsDialog.setOnClickListener {
                gameSettingsDialog.cancel()
            }
        }

        generalLastWordButton.setOnClickListener {
            val gameSettingsDialog = Dialog(this)
            gameSettingsDialog.setContentView(R.layout.dialog_game_settings)
            val closeGameSettingsDialog: Button =
                gameSettingsDialog.findViewById(R.id.closeGameSettingsDialog)
            val textGameSettingsDialog: TextView =
                gameSettingsDialog.findViewById(R.id.textGameSettingsDialog)
            textGameSettingsDialog.text =
                "Последнее слово каждого раунда могут угадывать все команды, в независимости от того, чей идет раунд"
            gameSettingsDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            gameSettingsDialog.show()
            closeGameSettingsDialog.setOnClickListener {
                gameSettingsDialog.cancel()
            }
        }

        robberyButton.setOnClickListener {
            val gameSettingsDialog = Dialog(this)
            gameSettingsDialog.setContentView(R.layout.dialog_game_settings)
            val closeGameSettingsDialog: Button =
                gameSettingsDialog.findViewById(R.id.closeGameSettingsDialog)
            val textGameSettingsDialog: TextView =
                gameSettingsDialog.findViewById(R.id.textGameSettingsDialog)
            textGameSettingsDialog.text =
                "Время от времени раунд какой-либо из команд будет становиться общим. Это означает,что в угадывании слов могут принимать участие все команды"
            gameSettingsDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            gameSettingsDialog.show()
            closeGameSettingsDialog.setOnClickListener {
                gameSettingsDialog.cancel()
            }
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