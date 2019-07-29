package com.mksengun.pizzaapp.ui.main

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.mksengun.pizzaapp.R
import org.koin.android.viewmodel.ext.android.viewModel


class PizzaMapFragment : Fragment() {

    companion object {
        fun newInstance() = PizzaMapFragment()
    }

    private val viewModel: PizzaMapViewModel by viewModel()

    private lateinit var googleMap: GoogleMap
    private var mMapView: MapView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.main_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getPizzaPlaces()

        mMapView = view.findViewById(R.id.mapView)
        mMapView?.onCreate(savedInstanceState)
        mMapView?.onResume()

        MapsInitializer.initialize(requireContext())

        viewModel.network.observe(viewLifecycleOwner, Observer {
            if (it == PizzaMapViewModel.Event.ERROR) {
                Snackbar.make(view, R.string.dialog_error_something_went_wrong, Snackbar.LENGTH_LONG).show()
            }
        })

        if (ContextCompat.checkSelfPermission(
                requireActivity(), ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                requireActivity(), ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            mMapView?.getMapAsync { mMap ->
                googleMap = mMap
                googleMap.isMyLocationEnabled = true
                viewModel.listOfPizzaPlaces.observe(viewLifecycleOwner, Observer { list ->
                    for (pizzaPlace in list) {
                        googleMap
                            .addMarker(
                                MarkerOptions()
                                    .position(LatLng(pizzaPlace.latitude, pizzaPlace.longitude))
                                    .title(pizzaPlace.name)
                                    .snippet(pizzaPlace.formattedAddress)

                            ).tag = pizzaPlace.id
                    }

                    if (!list.isNullOrEmpty()) {
                        googleMap.animateCamera(
                            CameraUpdateFactory.newCameraPosition(
                                CameraPosition.Builder().target(
                                    LatLng(list[0].latitude, list[0].longitude)
                                ).zoom(12f).build()
                            )
                        )
                        googleMap.setOnInfoWindowClickListener {
                            navigateToPizzaDetailFragment(it.tag as String)
                        }
                    }

                })
            }
        }
    }

    private fun navigateToPizzaDetailFragment(id: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, PizzaDetailFragment.newInstance(id))
            .commitAllowingStateLoss()
    }

}
