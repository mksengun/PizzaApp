package com.mksengun.pizzaapp.model.repository

import com.mksengun.pizzaapp.model.api.PizzaApi
import com.mksengun.pizzaapp.model.data.Pizza
import com.mksengun.pizzaapp.model.data.PizzaListResult
import retrofit2.Call
import retrofit2.Response

class PizzaRepository(private val apiService: PizzaApi) {

    fun getPizzaPlaces(onDataCallback: OnDataCallback) {
        apiService.getListOfPizzaPlaces().enqueue(object : retrofit2.Callback<PizzaListResult> {
            override fun onResponse(call: Call<PizzaListResult>, response: Response<PizzaListResult>) {
                if (response.isSuccessful)
                    onDataCallback.onReady((response.body() as PizzaListResult).list.places)
                else
                    onDataCallback.onFail()
            }

            override fun onFailure(call: Call<PizzaListResult>, t: Throwable) {
                onDataCallback.onFail()
            }
        })
    }

    interface OnDataCallback {
        fun onReady(data: List<Pizza>)
        fun onFail()
    }

}