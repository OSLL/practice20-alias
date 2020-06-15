package com.makentoshe.androidgithubcitemplate

import android.content.Intent
import android.app.Dialog
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_game_settings.*

class GameSettings : AppCompatActivity(), SeekBar.OnSeekBarChangeListener {

    lateinit var list: Array<MutableList<String>>
    lateinit var fineDialog: Dialog
    lateinit var closeFineDialog: TextView
    lateinit var wordDialog: Dialog
    lateinit var closeWordDialog: TextView
    lateinit var tasksDialog: Dialog
    lateinit var closeTasksDialog: TextView
    lateinit var robberyDialog: Dialog
    lateinit var closeRobberyDialog: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_settings)

        fineDialog = Dialog(this)
        wordDialog = Dialog(this)
        tasksDialog = Dialog(this)
        robberyDialog = Dialog(this)

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

        fineButton.setOnClickListener{
            showFineDialog()
        }

        generalLastWordButton.setOnClickListener{
            showGeneralLastWordDialog()
        }

        taskButton.setOnClickListener{
            showTasksDialog()
        }

        robberyButton.setOnClickListener{
            showRobberyDialog()
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

    fun showFineDialog(){
        fineDialog.setContentView(R.layout.fine_dialog)
        closeFineDialog = fineDialog.findViewById(R.id.okButton)
        fineDialog.show()
        closeFineDialog.setOnClickListener{
            fineDialog.cancel()
        }
    }

    fun showGeneralLastWordDialog(){
        wordDialog.setContentView(R.layout.word_dialog)
        closeWordDialog = wordDialog.findViewById(R.id.okButton)
        wordDialog.show()
        closeWordDialog.setOnClickListener{
            wordDialog.cancel()
        }
    }

    fun showTasksDialog(){
        tasksDialog.setContentView(R.layout.tasks_dialog)
        closeTasksDialog = tasksDialog.findViewById(R.id.okButton)
        tasksDialog.show()
        closeTasksDialog.setOnClickListener{
            tasksDialog.cancel()
        }
    }

    fun showRobberyDialog(){
        robberyDialog.setContentView(R.layout.robbery_dialog)
        closeRobberyDialog = robberyDialog.findViewById(R.id.okButton)
        robberyDialog.show()
        closeRobberyDialog.setOnClickListener{
            robberyDialog.cancel()
        }
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {}

    override fun onStartTrackingTouch(p0: SeekBar?) {}

    override fun onStopTrackingTouch(p0: SeekBar?) {}


}