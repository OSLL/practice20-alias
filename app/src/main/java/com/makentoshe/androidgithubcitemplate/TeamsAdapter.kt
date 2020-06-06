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
    val deleteNoActive: Drawable
    ) :
    RecyclerView.Adapter<TeamsAdapter.TeamsAdapterHolder>() {

    var contain = MutableList(teamsNames.size, { it -> false })


    fun textChanged(a: EditText, position: Int, teamNameLayout: TextInputLayout): String {

        b.isClickable = false

        a.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(p0: Editable?) {
                teamsNames[position] = a.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                teamNameLayout.error = "Имя не должно быть пустым"
                contain[position] = false
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

    class TeamsAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName = itemView.findViewById<EditText>(R.id.teamName)
        val buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)
        val teamNameLayout = itemView.findViewById<TextInputLayout>(R.id.teamNameLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsAdapterHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.teams_item, parent, false)
        return TeamsAdapterHolder(view)
    }

    override fun getItemCount(): Int = teamsNames.size

    override fun onBindViewHolder(holder: TeamsAdapterHolder, position: Int) {
        if (teamsNames.size <= 2) {
            holder.buttonDelete.isClickable = false
            holder.buttonDelete.background = deleteNoActive
        } else {
            holder.buttonDelete.isClickable = true
            holder.buttonDelete.background = deleteActive
        }

        teamsNames[position] = textChanged(holder.teamName, position, holder.teamNameLayout)
        holder.teamNameLayout.error = "Имя не должно быть пустым"
    }

    fun getter(): Array<String> = teamsNames.toTypedArray()
}