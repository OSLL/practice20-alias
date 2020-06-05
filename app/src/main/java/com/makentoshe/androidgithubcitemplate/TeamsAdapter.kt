package com.makentoshe.androidgithubcitemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TeamsAdapter(var context: Context, var teamsNames: MutableList<String>) :
    RecyclerView.Adapter<TeamsAdapter.TeamsAdapterHolder>() {

    var a: Array<String> = teamsNames.toTypedArray()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsAdapterHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.teams_item, parent, false)
        return TeamsAdapterHolder(view)
    }

    override fun getItemCount(): Int = teamsNames.size

    override fun onBindViewHolder(holder: TeamsAdapterHolder, position: Int) {
        holder.teamName.text = teamsNames[position]
        if (teamsNames.size <= 2) holder.buttonDelete.visibility = View.GONE
        teamsNames[position]=holder.teamName.text.toString()
    }

    class TeamsAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName = itemView.findViewById<TextView>(R.id.teamName)
        val buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)
    }

    fun getter(): Array<String> = teamsNames.toTypedArray()
}




