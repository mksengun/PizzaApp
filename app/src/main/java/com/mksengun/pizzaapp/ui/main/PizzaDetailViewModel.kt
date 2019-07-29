package com.mksengun.pizzaapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mksengun.pizzaapp.model.data.Pizza
import com.mksengun.pizzaapp.model.repository.PizzaRepository
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class PizzaDetailViewModel(private val pizzaPlaceId: String) : ViewModel(), KoinComponent {

    enum class Event {
        ERROR,
        SUCCESS,
    }

    var pizzaPlace = MutableLiveData<Pizza>()


    private val _network = MutableLiveData<Event>()
    val network: LiveData<Event> get() = _network

    private val repository: PizzaRepository by inject()

    init {
        getPizzaPlace()
    }

    private fun getPizzaPlace() {
        repository.getPizzaPlace(pizzaPlaceId, object : PizzaRepository.OnDataCallback {
            override fun onReady(data: Any) {
                pizzaPlace.value = data as Pizza
                _network.value = Event.SUCCESS
            }

            override fun onFail() {
                _network.value = Event.ERROR
            }
        })
    }

}
