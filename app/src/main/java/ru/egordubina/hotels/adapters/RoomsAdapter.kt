package ru.egordubina.hotels.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import ru.egordubina.domain.utils.toRubInt
import ru.egordubina.hotels.R
import ru.egordubina.hotels.models.RoomUi

class RoomsAdapter(
    private val apartmentsItems: List<RoomUi>,
    private val setImagesSliderAdapter: (List<String>) -> ImageSliderAdapter,
    private val setChips: (List<String>) -> List<Chip>,
    private val onButtonClick: () -> Unit,
) : RecyclerView.Adapter<RoomsAdapter.RoomsViewHolder>() {
    class RoomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // todo: переписать на binding
        val name: TextView = itemView.findViewById(R.id.item__apartment_name)
        val price: TextView = itemView.findViewById(R.id.item__apartment_price)
        val pricePer: TextView = itemView.findViewById(R.id.item__apartment_price_per)
        val imagesSlider: ViewPager2 =
            itemView.findViewById(R.id.view_pager__apartment_images_slider)
        val chipGroup: ChipGroup = itemView.findViewById(R.id.chip_group__peculiarities)
        val buttonSelectRoom: Button = itemView.findViewById(R.id.button__select_room)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder =
        RoomsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item__room, parent, false)
        )

    override fun getItemCount(): Int = apartmentsItems.size

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        with(holder) {
            name.text = apartmentsItems[position].name
            price.text = apartmentsItems[position].price.toRubInt()
            imagesSlider.adapter = setImagesSliderAdapter(apartmentsItems[position].imagesUrls)
            pricePer.text = apartmentsItems[position].pricePer
            buttonSelectRoom.setOnClickListener { onButtonClick() }
            setChips(apartmentsItems[position].peculiarities).forEach { chipGroup.addView(it) }
        }
    }
}