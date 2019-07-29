package com.mksengun.pizzaapp.model.data

data class Pizza(
    val id: String,
    val name: String,
    val phone: String,
    val website: String,
    val formattedAddress: String,
    val city: String,
    val openingHours: List<String>,
    val longitude: Double,
    val latitude: Double,
    val images: List<Image>,
    val friendIds: List<String>
)

data class Image(
    val id: String,
    val url: String,
    val caption: String,
    val expiration: String
)