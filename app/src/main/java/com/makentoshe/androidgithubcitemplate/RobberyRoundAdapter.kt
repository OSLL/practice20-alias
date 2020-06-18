package com.makentoshe.androidgithubcitemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RobberyRoundAdapter(var context: Context,
                          private var teamsNames: Array<String>,
                          private var teamsScore: IntArray) :
    RecyclerView.Adapter<RobberyRoundAdapter.RobberyRoundAdapterHolder>() {
    private var wordCounterText:MutableList<TextView> = mutableListOf()

    interface OnItemClickListener{
        fun onCheckClicked(position: Int)
    }

    private lateinit var mListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    class RobberyRoundAdapterHolder(itemView: View,listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val teamRobbery = itemView.findViewById<TextView>(R.id.teamRobbery)!!
        private val checkRobbery = itemView.findViewById<Button>(R.id.checkRobbery)!!
        val counterRobbery = itemView.findViewById<TextView>(R.id.counterRobbery)!!
        init {
            checkRobbery.setOnClickListener {
                val position = adapterPosition
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
        wordCounterText.add(holder.counterRobbery)
    }
}