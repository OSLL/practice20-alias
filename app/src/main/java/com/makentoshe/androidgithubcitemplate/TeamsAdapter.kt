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
    private var teamsNames: MutableList<String>,
    private var currentPosition: Int
    ) : RecyclerView.Adapter<TeamsAdapter.TeamsAdapterHolder>() {

    interface OnItemClickListener{
        fun onDeleteClicked(position: Int){}
        fun onEditClicked(position: Int)
    }

    private lateinit var mListener: OnItemClickListener

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    fun currentMinus(){
        currentPosition--
    }

    class TeamsAdapterHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {

        val teamName = itemView.findViewById<TextView>(R.id.teamName)!!
        private val buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)!!
        private val buttonEdit = itemView.findViewById<Button>(R.id.buttonEdit)!!

        init {
            buttonDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    listener.onDeleteClicked(position)
            }

            buttonEdit.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION)
                    listener.onEditClicked(position)
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

        holder.teamName.text = teamsNames[position]
    }
}