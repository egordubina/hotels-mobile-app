package ru.egordubina.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.egordubina.domain.models.Apartment as DomainApartment

@Serializable
data class Apartment(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("price") val price: Int,
    @SerialName("price_per") val pricePer: String,
    @SerialName("peculiarities") val peculiarities: List<String>,
    @SerialName("image_urls") val imagesUrls: List<String>,
)

fun Apartment.asDomain(): DomainApartment = DomainApartment(
    id = this.id,
    name = this.name,
    price = this.price,
    pricePer = this.pricePer.lowercase(),
    peculiarities = this.peculiarities,
    imagesUrls = this.imagesUrls
)

fun List<Apartment>.asDomain(): List<DomainApartment> = map { it.asDomain() }