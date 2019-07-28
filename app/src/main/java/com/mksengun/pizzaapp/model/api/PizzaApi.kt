package com.mksengun.pizzaapp.model.api

import com.mksengun.pizzaapp.model.data.Friend
import com.mksengun.pizzaapp.model.data.Pizza
import com.mksengun.pizzaapp.model.data.PizzaListResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PizzaApi {

    @GET("pizzaplaces")
    fun getListOfPizzaPlaces(): Call<PizzaListResult>

    @GET("pizzaplaces")
    fun getDetailOfPizza(@Query("id") id: String): Call<Pizza>

    @GET("friends")
    fun getListOfFriends(@Query("id") id: String): Call<List<Friend>>

}