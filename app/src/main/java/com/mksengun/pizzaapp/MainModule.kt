package com.mksengun.pizzaapp

import com.mksengun.pizzaapp.model.api.PizzaApi
import com.mksengun.pizzaapp.model.repository.PizzaRepository
import com.mksengun.pizzaapp.ui.main.PizzaDetailViewModel
import com.mksengun.pizzaapp.ui.main.PizzaMapViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val mainModule = module {

    single { PizzaRepository(get()) }

    single { createWebService() }

    viewModel { PizzaMapViewModel() }

    viewModel { (id: String) -> PizzaDetailViewModel(pizzaPlaceId = id) }

}

fun createWebService(): PizzaApi {
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl("http://demo4327201.mockable.io/pizza-api/")
        .build()

    return retrofit.create(PizzaApi::class.java)
}