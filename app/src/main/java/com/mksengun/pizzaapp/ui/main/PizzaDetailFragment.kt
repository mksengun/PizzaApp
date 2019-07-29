package com.mksengun.pizzaapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mksengun.pizzaapp.R
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PizzaDetailFragment : Fragment() {

    companion object {
        private const val ARGUMENT_PIZZA_PLACE_ID = "pizzaPlaceId"

        @JvmStatic
        fun newInstance(pizzaPlaceId: String): PizzaDetailFragment {
            return PizzaDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_PIZZA_PLACE_ID, pizzaPlaceId)
                }
            }
        }
    }

    val viewModel: PizzaDetailViewModel by viewModel {
        parametersOf(arguments?.getString(ARGUMENT_PIZZA_PLACE_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

}
