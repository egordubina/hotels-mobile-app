package ru.egordubina.hotels.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import ru.egordubina.hotels.R

class ImageSliderAdapter(private val imagesUrls: List<String>) :
    RecyclerView.Adapter<ImageSliderAdapter.ImagesViewHolder>() {
    class ImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ShapeableImageView = itemView.findViewById(R.id.image__hotel_slider)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder =
        ImagesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item__image, parent, false)
        )

    override fun getItemCount(): Int = imagesUrls.size

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        holder.image.load(imagesUrls[position]) {
            crossfade(true)
            allowHardware(false)
        }
    }
}