package com.threemonkeys.alias

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TeamsAdapterGame(
    var context: Context,
    private var teamsNames: Array<String>,
    private var teamsScore: Array<Int>
) :
    RecyclerView.Adapter<TeamsAdapterGame.TeamsAdapterGameHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TeamsAdapterGameHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.team_game_item, parent, false)
        return TeamsAdapterGameHolder(view)
    }

    override fun getItemCount(): Int = teamsNames.size

    override fun onBindViewHolder(holder: TeamsAdapterGameHolder, position: Int) {
        holder.teamGameName.text = teamsNames[position]
        holder.teamGameCounter.text = teamsScore[position].toString()
    }

    class TeamsAdapterGameHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val teamGameName = itemView.findViewById<TextView>(R.id.teamGameName)!!
        val teamGameCounter = itemView.findViewById<TextView>(R.id.teamGameCounter)!!

    }
}