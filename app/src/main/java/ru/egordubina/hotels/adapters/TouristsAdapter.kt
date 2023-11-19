package ru.egordubina.hotels.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.egordubina.hotels.R
import ru.egordubina.hotels.models.TouristUi

class TouristsAdapter(private val tourists: List<TouristUi>) :
    RecyclerView.Adapter<TouristsAdapter.TouristsViewHolder>() {
    class TouristsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristsViewHolder =
        TouristsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item__tourist, parent, false)
        )

    override fun getItemCount(): Int = tourists.size

    override fun onBindViewHolder(holder: TouristsViewHolder, position: Int) {
        with(holder) {}
    }
}