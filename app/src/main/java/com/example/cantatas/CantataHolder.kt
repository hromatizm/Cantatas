package com.example.cantatas

import android.view.View
import android.widget.AdapterView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CantataHolder(view: View, private val listener: RecyclerCantataAdapter.OnCantataClickListener) :
    RecyclerView.ViewHolder(view) {

    var bwv = view.findViewById<TextView>(R.id.bwv)
    var name = view.findViewById<TextView>(R.id.name)
    var date = view.findViewById<TextView>(R.id.date)
    var occasion = view.findViewById<TextView>(R.id.occasion)
    var textBy = view.findViewById<TextView>(R.id.textBy)
    var rating = view.findViewById<RatingBar>(R.id.rating)
    lateinit var cantata: Cantata

    init {
        itemView.setOnClickListener { listener.onCantataClick(cantata) }
    }

    fun bind(cantata: Cantata) {
        this.cantata = cantata
        bwv.text = cantata.bwv
        name.text = cantata.name
        date.text = cantata.date
        occasion.text = cantata.occasion
        textBy.text = cantata.textBy
        rating.rating = cantata.rating

    }
}