package ru.egordubina.hotels.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ru.egordubina.hotels.databinding.ItemTouristBinding
import ru.egordubina.hotels.models.TouristUi

class TouristsAdapter(
    private val onTouristButtonClick: (Int, TouristUi) -> Unit
) : RecyclerView.Adapter<TouristsAdapter.TouristsViewHolder>() {

    private var tourists: List<TouristUi> = emptyList()

    fun submitList(newList: List<TouristUi>) {
        tourists = newList
        notifyItemInserted(newList.size)
    }
    class TouristsViewHolder(val binding: ItemTouristBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristsViewHolder =
        TouristsViewHolder(
            ItemTouristBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun getItemCount(): Int = tourists.size

    override fun onBindViewHolder(holder: TouristsViewHolder, position: Int) {
        with(holder) {
            binding.apply {
                textViewTouristNumber.text = "Турист №${position + 1}"
                layoutTouristData.isVisible = tourists[position].isVisible
                buttonCollapseTourist.setOnClickListener {
                    onTouristButtonClick(position, tourists[position])
                }
            }
        }
    }
}