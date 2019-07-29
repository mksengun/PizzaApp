package com.mksengun.pizzaapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.mksengun.pizzaapp.R
import com.squareup.picasso.Picasso
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

    private val viewModel: PizzaDetailViewModel by viewModel {
        parametersOf(arguments?.getString(ARGUMENT_PIZZA_PLACE_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.network.observe(viewLifecycleOwner, Observer {
            if (it == PizzaDetailViewModel.Event.ERROR) {
                Snackbar.make(view, R.string.dialog_error_something_went_wrong, Snackbar.LENGTH_LONG).show()
            }
        })

        val toolbar = view.findViewById(R.id.toolbar) as Toolbar

        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material)
        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        viewModel.pizzaPlace.observe(viewLifecycleOwner, Observer {

            var openingHours = ""
            for (day in it.openingHours) {
                openingHours += "$day\n"
            }

            toolbar.title = it.name

            Picasso.with(view.context)
                .load(it.images[0].url)
                .placeholder(R.color.colorPrimary)
                .into(view.findViewById<ImageView>(R.id.iv_main))

            view.findViewById<TextView>(R.id.tv_city).text = it.city
            view.findViewById<TextView>(R.id.tv_formatted_address).text = it.formattedAddress
            view.findViewById<TextView>(R.id.tv_phone).text = it.phone
            view.findViewById<TextView>(R.id.tv_website).text = it.website
            view.findViewById<TextView>(R.id.tv_openning_hours).text = openingHours

        })

    }
}
