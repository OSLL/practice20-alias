package com.threemonkeys.alias

import android.app.Dialog
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_rules.*

class Rules : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules)
        val appPrefs: SharedPreferences = getSharedPreferences("AppPrefs", 0)
        val prefsEditor: SharedPreferences.Editor = appPrefs.edit()

        var isNightModeOn = appPrefs.getBoolean("isNightModeOn", false)

        if (isNightModeOn){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        settings.setOnClickListener {
            val settingsDialog = Dialog(this)
            settingsDialog.setContentView(R.layout.dialog_settings)
            val changeTheme: Switch =
                settingsDialog.findViewById(R.id.themeChanger)
            changeTheme.isChecked = isNightModeOn
            settingsDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            settingsDialog.show()
            settingsDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            changeTheme.setOnCheckedChangeListener { buttonView, isChecked ->
                isNightModeOn = isChecked
                if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                prefsEditor.putBoolean("isNightModeOn", isNightModeOn)
                prefsEditor.apply()
                settingsDialog.cancel()
            }
        }

        back_button.setOnClickListener {
            finish()
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_down)
    }
}