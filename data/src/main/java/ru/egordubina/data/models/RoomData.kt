package ru.egordubina.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.egordubina.domain.models.RoomsDomain as DomainApartment

@Serializable
data class RoomData(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("price") val price: Int,
    @SerialName("price_per") val pricePer: String,
    @SerialName("peculiarities") val peculiarities: List<String>,
    @SerialName("image_urls") val imagesUrls: List<String>,
)

fun RoomData.asDomain(): DomainApartment = DomainApartment(
    id = this.id,
    name = this.name,
    price = this.price,
    pricePer = this.pricePer.lowercase(),
    peculiarities = this.peculiarities,
    imagesUrls = this.imagesUrls
)

fun List<RoomData>.asDomain(): List<DomainApartment> = map { it.asDomain() }