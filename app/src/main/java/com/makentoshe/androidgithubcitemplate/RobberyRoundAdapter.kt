package com.makentoshe.androidgithubcitemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RobberyRoundAdapter(var context: Context,
                          var teamsNames: Array<String>,
                          var teamsScore: IntArray) :
    RecyclerView.Adapter<RobberyRoundAdapter.RobberyRoundAdapterHolder>() {
    interface onItemClickListener{
        fun onCheckClicked(position: Int)
    }

    lateinit var mListener: onItemClickListener

    fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    class RobberyRoundAdapterHolder(itemView: View,listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val teamRobbery = itemView.findViewById<TextView>(R.id.teamRobbery)!!
        val checkRobbery = itemView.findViewById<Button>(R.id.checkRobbery)!!
        val counterRobbery = itemView.findViewById<TextView>(R.id.counterRobbery)!!
        init {
            checkRobbery.setOnClickListener {
                var position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    listener.onCheckClicked(position)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RobberyRoundAdapterHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_robbery_round, parent, false)
        return RobberyRoundAdapterHolder(view, mListener)
    }

    override fun getItemCount(): Int = teamsNames.size

    override fun onBindViewHolder(holder: RobberyRoundAdapterHolder, position: Int) {
        holder.teamRobbery.text = teamsNames[position]
        holder.counterRobbery.text = teamsScore[position].toString()
    }

}