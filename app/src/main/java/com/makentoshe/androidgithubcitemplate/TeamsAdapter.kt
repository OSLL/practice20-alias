package com.makentoshe.androidgithubcitemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TeamsAdapter(var context: Context, var teamsNames: Array<String>) : RecyclerView.Adapter<TeamsAdapter.TeamsAdapterHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsAdapterHolder {
        var inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.teams_item, parent, false)
        return TeamsAdapterHolder(view)
    }

    override fun getItemCount(): Int = teamsNames?.size!!

    override fun onBindViewHolder(holder: TeamsAdapterHolder, position: Int) {
        holder.teamName.text = teamsNames.get(position)
    }

    class TeamsAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamName = itemView.findViewById<TextView>(R.id.teamName)
    }

}




