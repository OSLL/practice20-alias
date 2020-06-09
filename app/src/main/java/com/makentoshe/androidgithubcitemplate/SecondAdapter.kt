package com.makentoshe.androidgithubcitemplate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SecondAdapter(
    var context: Context,
    var plusPonits: Int,
    var minusPonits: Int,
    var roundNumber: Int) : RecyclerView.Adapter<SecondAdapter.SecondAdapterHolder>() {

    class SecondAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val knowSecond = itemView.findViewById<TextView>(R.id.knowSecond)
        val skipSecond = itemView.findViewById<TextView>(R.id.skipSecond)
        val roundNameSecond = itemView.findViewById<TextView>(R.id.roundNameSecond)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SecondAdapter.SecondAdapterHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.second_item, parent, false)
        return SecondAdapter.SecondAdapterHolder(view)
    }

    override fun getItemCount(): Int = 2

    override fun onBindViewHolder(holder: SecondAdapter.SecondAdapterHolder, position: Int) {
        holder.roundNameSecond.text = "${roundNumber} раунд"
        holder.knowSecond.text = plusPonits.toString()
        holder.skipSecond.text = minusPonits.toString()
    }
}