package com.threemonkeys.alias

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SecondAdapter(
    var context: Context,
    private var roundInfo: MutableList<String>) : RecyclerView.Adapter<SecondAdapter.SecondAdapterHolder>() {

    class SecondAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val knowSecond: TextView = itemView.findViewById(R.id.knowSecond)
        val skipSecond: TextView = itemView.findViewById(R.id.skipSecond)
        val roundNameSecond: TextView = itemView.findViewById(R.id.roundNameSecond)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondAdapterHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.second_item, parent, false)
        return SecondAdapterHolder(view)
    }

    override fun getItemCount(): Int = roundInfo.size

    override fun onBindViewHolder(holder: SecondAdapterHolder, position: Int) {
        holder.roundNameSecond.text = "${position+1} раунд"
        holder.knowSecond.text = roundInfo[position].substringBefore('.')
        holder.skipSecond.text = roundInfo[position].substringAfter('.')
    }
}