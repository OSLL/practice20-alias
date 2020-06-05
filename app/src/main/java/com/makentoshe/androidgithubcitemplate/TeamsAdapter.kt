package com.makentoshe.androidgithubcitemplate

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView

class TeamsAdapter(var context: Context, var teamsNames: MutableList<String>) :
    RecyclerView.Adapter<TeamsAdapter.TeamsAdapterHolder>() {

    fun textChanged(a:EditText,position: Int) : String{
        a.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                teamsNames[position]= a.text.toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int){
                teamsNames[position]= a.text.toString()
            }
        })
        return a.text.toString()
    }

    class TeamsAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName = itemView.findViewById<EditText>(R.id.teamName)
        val buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsAdapterHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.teams_item, parent, false)
        return TeamsAdapterHolder(view)

    }

    override fun getItemCount(): Int = teamsNames.size

    override fun onBindViewHolder(holder: TeamsAdapterHolder, position: Int) {
        holder.teamName.setText(teamsNames[position])

        if (teamsNames.size <= 2) holder.buttonDelete.visibility = View.GONE
        teamsNames[ position]= textChanged(holder.teamName,position)
        }

    fun getter(): Array<String> = teamsNames.toTypedArray()
    }










