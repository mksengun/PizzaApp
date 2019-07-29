package com.mksengun.pizzaapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mksengun.pizzaapp.model.data.Pizza
import com.mksengun.pizzaapp.model.repository.PizzaRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PizzaMapViewModel : ViewModel(), KoinComponent {

    enum class Event {
        ERROR,
        SUCCESS,
    }

    private val _network = MutableLiveData<Event>()
    val network: LiveData<Event> get() = _network

    private val repository: PizzaRepository by inject()

    var listOfPizzaPlaces = MutableLiveData<List<Pizza>>()

    init {
        listOfPizzaPlaces.value = listOf()
    }

    fun getPizzaPlaces() {
        repository.getPizzaPlaces(object : PizzaRepository.OnDataCallback {
            override fun onReady(data: Any) {
                listOfPizzaPlaces.value = data as List<Pizza>
                _network.value = Event.SUCCESS
            }

            override fun onFail() {
                _network.value = Event.ERROR
            }
        })
    }

}

