package com.mksengun.pizzaapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mksengun.pizzaapp.ui.main.PizzaMapFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            checkPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PizzaMapFragment.newInstance())
                        .commitNow()
                } else {
                    AlertDialog.Builder(this)
                        .setTitle(R.string.dialog_request_location_title)
                        .setMessage(R.string.dialog_request_location_message)
                        .setPositiveButton(android.R.string.yes) { _, _ ->
                            checkPermissions()
                        }
                        .setNegativeButton(android.R.string.no, null)
                        .show()
                }
                return
            }
            else -> {
                Toast.makeText(this, "WTF?", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                MY_PERMISSIONS_REQUEST_LOCATION
            )
        } else {
            navigateToPizzaMapFragment()
        }
    }

    private fun navigateToPizzaMapFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, PizzaMapFragment.newInstance())
            .commitNow()
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST_LOCATION = 0x01
    }
}
