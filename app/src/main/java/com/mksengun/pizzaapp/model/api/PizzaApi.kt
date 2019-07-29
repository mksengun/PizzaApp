package com.mksengun.pizzaapp.model.api

import com.mksengun.pizzaapp.model.data.Friend
import com.mksengun.pizzaapp.model.data.Pizza
import com.mksengun.pizzaapp.model.data.PizzaListResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PizzaApi {

    @GET("pizzaplaces")
    fun getListOfPizzaPlaces(): Call<PizzaListResult>

    @GET("pizzaplaces/{id}")
    fun getDetailOfPizza(@Path("id") id: String): Call<Pizza>

    @GET("friends")
    fun getListOfFriends(): Call<List<Friend>>

}