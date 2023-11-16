package ru.egordubina.hotels.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.egordubina.domain.models.Apartment
import ru.egordubina.hotels.R

class ApartmentsAdapter(private var apartmentsItems: List<Apartment>) :
    RecyclerView.Adapter<ApartmentsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.item__apartment_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item__apartment, parent, false))

    override fun getItemCount(): Int = apartmentsItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = apartmentsItems[position].name
    }
}