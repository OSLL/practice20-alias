package com.makentoshe.androidgithubcitemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TeamsAdapter(
    var context: Context,
    var teamsNames: MutableList<String>,
    var currentPosition: Int
    ) : RecyclerView.Adapter<TeamsAdapter.TeamsAdapterHolder>() {

    interface onItemClickListener{
        fun onDeleteClicked(position: Int){}
    }

    lateinit var mListener: onItemClickListener

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    fun currentMinus(){
        currentPosition--
    }

    class TeamsAdapterHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val teamName = itemView.findViewById<TextView>(R.id.teamName)
        val buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)

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

        holder.teamName.setText("${teamsNames[position]}")
    }

    fun getter(): Array<String> = teamsNames.toTypedArray()

}