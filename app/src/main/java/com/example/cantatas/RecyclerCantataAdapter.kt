package com.example.cantatas

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class RecyclerCantataAdapter(
    private val cantatasData: List<Cantata>
) : RecyclerView.Adapter<CantataHolder>() {

    private var listener: OnCantataClickListener? = null

    override fun getItemCount() = cantatasData.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CantataHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.listview_item, parent, false)
        return CantataHolder(view, listener!!)
    }

    override fun onBindViewHolder(holder: CantataHolder, position: Int) {
        holder.bind(cantatasData[position])
    }

    interface OnCantataClickListener {
        fun onCantataClick(cantata: Cantata)
    }

    fun setOnCantataClickListener(listener: OnCantataClickListener) {
        this.listener = listener
    }
}