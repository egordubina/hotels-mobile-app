package ru.egordubina.hotels.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.ChipGroup
import ru.egordubina.domain.models.Apartment
import ru.egordubina.domain.utils.toRubInt
import ru.egordubina.hotels.R

class ApartmentsAdapter(private val apartmentsItems: List<Apartment>, private val onItemClick: () -> Unit) :
    RecyclerView.Adapter<ApartmentsAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // todo: переписать на binding
        val name: TextView = itemView.findViewById(R.id.item__apartment_name)
        val price: TextView = itemView.findViewById(R.id.item__apartment_price)
        val priceFor: TextView = itemView.findViewById(R.id.item__apartment_price_for)
        val imagesSlider: ViewPager2 = itemView.findViewById(R.id.view_pager__apartment_images_slider)
        val chipGroup: ChipGroup = itemView.findViewById(R.id.chip_group__peculiarities)
        val buttonSelectRoom: Button = itemView.findViewById(R.id.button__select_room)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item__apartment, parent, false))

    override fun getItemCount(): Int = apartmentsItems.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = apartmentsItems[position].name
        holder.price.text = apartmentsItems[position].price.toRubInt()
        // fixme: костыль убрать этот и сделать нормально (но как?)
        holder.imagesSlider.adapter = ImageSliderAdapter(apartmentsItems[position].imagesUrls)
        holder.priceFor.text = apartmentsItems[position].pricePer
        // todo: дописать отображение чипов
        holder.buttonSelectRoom.setOnClickListener { onItemClick() }
//        holder.chipGroup.removeAllViews()
//        apartmentsItems[position].peculiarities.forEach()
    }
}