package com.alicimsamil.polygonproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.MutableLiveData

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.alicimsamil.polygonproject.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.ButtCap
import com.google.android.gms.maps.model.PolygonOptions
import com.google.maps.android.SphericalUtil

class MapsActivity : AppCompatActivity(), OnMapReadyCallback,GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private  var points = ArrayList<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val backButton=findViewById<Button>(R.id.backBtn)
        backButton.setOnClickListener {
            if(points.size<=1) {
                points.clear()
                mMap.clear()
            }
            else{
                points.removeLast()
                drawPolygon()
            }
        }

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)

    }



    private fun drawPolygon(){
        mMap.clear()
        val polygon = PolygonOptions()
        points.forEach {
            mMap.addMarker(MarkerOptions().position(it))
            polygon.add(it)
        }
        findViewById<TextView>(R.id.squareFeet).text="${SphericalUtil.computeArea(points)}"
        polygon.fillColor(R.color.purple_700)
        mMap.addPolygon(polygon)
    }



    override fun onMapLongClick(p0: LatLng) {
        points.add(p0)
        drawPolygon()
    }
}

