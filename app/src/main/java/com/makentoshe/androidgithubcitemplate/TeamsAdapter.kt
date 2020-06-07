package com.makentoshe.androidgithubcitemplate

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout

class TeamsAdapter(
    var context: Context,
    var teamsNames: MutableList<String>,
    var b: Button,
    val noActiveColor: ColorStateList,
    val activeColor: ColorStateList,
    val deleteActive: Drawable,
    val deleteNoActive: Drawable,
    var currentPosition: Int
    ) : RecyclerView.Adapter<TeamsAdapter.TeamsAdapterHolder>() {

    interface onItemClickListener{
        fun onDeleteClicked(position: Int){}
    }

    lateinit var mListener: onItemClickListener

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    var contain = MutableList(currentPosition, { false })

    fun adderFunction(){
        contain.add(false)
        currentPosition++
        if (!contain.contains(false)) {
            b.isClickable = true
            b.setTextColor(activeColor)
        } else {
            b.isClickable = false
            b.setTextColor(noActiveColor)
        }
    }

    fun currentMinus(){
        currentPosition--
    }

    var buttons: MutableList<Button> = mutableListOf()

    var texts: MutableList<TextInputLayout> = mutableListOf()

    fun buttonsGetter(): MutableList<Button> = buttons

    fun textsGetter(): MutableList<TextInputLayout> = texts

    fun textChanged(a: EditText, position: Int, teamNameLayout: TextInputLayout): String {


        a.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                teamsNames[position] = a.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                teamsNames[position] = a.text.toString()

                if (teamNameLayout.editText?.text.toString()
                        .isEmpty() || teamNameLayout.editText?.text.toString().isBlank()
                ) {
                    teamNameLayout.error = "Имя не должно быть пустым"
                    contain[position] = false
                }

                if (teamNameLayout.editText?.text.toString()
                        .isNotEmpty() && teamNameLayout.editText?.text.toString().isNotBlank()
                ) {
                    teamNameLayout.isErrorEnabled = false
                    teamNameLayout.isErrorEnabled = true
                    contain[position] = true
                }

                if (teamNameLayout.editText?.text.toString().length>25) {
                    teamNameLayout.error = "Слишком длинное имя"
                    contain[position] = false
                }

                if (!contain.contains(false)) {
                    b.isClickable = true
                    b.setTextColor(activeColor)
                } else {
                    b.isClickable = false
                    b.setTextColor(noActiveColor)
                }
            }

        })
        return a.text.toString()
    }



    class TeamsAdapterHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val teamName = itemView.findViewById<EditText>(R.id.teamName)
        val buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)
        val teamNameLayout = itemView.findViewById<TextInputLayout>(R.id.teamNameLayout)

        init {
            buttonDelete.setOnClickListener {
                var position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    listener.onDeleteClicked(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsAdapterHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.teams_item, parent, false)
        return TeamsAdapterHolder(view, mListener)
    }

    override fun getItemCount(): Int = teamsNames.size

    override fun onBindViewHolder(holder: TeamsAdapterHolder, position: Int) {

        buttons.add(holder.buttonDelete)
        texts.add(holder.teamNameLayout)
//     holder.teamNameLayout.editText?.setText("${currentPosition} team")
//        holder.teamName.setText("${currentPosition} team")

        if (contain.size<=2) {
            holder.buttonDelete.isClickable = false
            holder.buttonDelete.background = deleteNoActive
        }

        teamsNames[position] = textChanged(holder.teamName, position, holder.teamNameLayout)
        if (holder.teamNameLayout.editText?.text.toString().isEmpty() || holder.teamNameLayout.editText?.text.toString().isBlank() ) holder.teamNameLayout.error = "Имя не должно быть пустым"
        if (holder.teamNameLayout.editText?.text.toString().length>25) holder.teamNameLayout.error = "Слишком длинное имя"
    }

    fun getter(): Array<String> = teamsNames.toTypedArray()

}