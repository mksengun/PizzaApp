package com.mksengun.pizzaapp.model.data

data class PizzaListResult(
    val meta: Meta,
    val list: ListOfPlaces
)

data class Meta(
    val total: Int,
    val precision: Int
)

data class ListOfPlaces(
    val list: List<Pizza>
)

