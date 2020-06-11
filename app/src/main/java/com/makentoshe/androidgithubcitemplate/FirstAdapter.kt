package com.makentoshe.androidgithubcitemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FirstAdapter(
    var context: Context,
    var teamsScores: Array<MutableList<String>>,
    var teamsNames: MutableList<String>) : RecyclerView.Adapter<FirstAdapter.FirstAdapterHolder>() {

    class FirstAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val secondRecycler = itemView.findViewById<RecyclerView>(R.id.secondRecycler)
        val teamNameFirst = itemView.findViewById<TextView>(R.id.teamNameFirst)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstAdapter.FirstAdapterHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.first_item, parent, false)
        return FirstAdapter.FirstAdapterHolder(view)
    }

    override fun getItemCount(): Int = teamsScores.size

    override fun onBindViewHolder(holder: FirstAdapter.FirstAdapterHolder, position: Int) {

        holder.teamNameFirst.text = teamsNames[position]

        var secondAdapter = SecondAdapter(context, teamsScores[position])
        holder.secondRecycler.adapter = secondAdapter
        holder.secondRecycler.layoutManager = LinearLayoutManager(context)

    }
}