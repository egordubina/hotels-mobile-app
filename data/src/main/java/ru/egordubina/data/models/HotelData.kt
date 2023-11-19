package ru.egordubina.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.egordubina.domain.models.HotelDomain

@Serializable
data class HotelData(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("adress") val address: String,
    @SerialName("minimal_price") val minimalPrice: Int,
    @SerialName("price_for_it") val priceForIt: String,
    @SerialName("rating") val rating: Int,
    @SerialName("rating_name") val ratingName: String,
    @SerialName("image_urls") val imagesUrls: List<String>,
    @SerialName("about_the_hotel") val aboutHotel: InfoAboutHotelData,
)

fun HotelData.asDomain(): HotelDomain = HotelDomain(
    id = this.id,
    name = this.name,
    address = this.address,
    minimalPrice = this.minimalPrice,
    priceForIt = this.priceForIt.lowercase(),
    rating = this.rating,
    ratingName = this.ratingName,
    imagesUrls = this.imagesUrls,
    aboutHotel = this.aboutHotel.asDomain(),
)