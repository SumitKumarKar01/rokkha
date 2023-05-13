package com.example.rokkha

import android.graphics.Color
import android.net.http.HttpResponseCache.install
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rokkha.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient
import com.google.gson.Gson

import io.ktor.client.engine.android.Android
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.plugins.kotlinx.serializer.KotlinxSerializer
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.URLBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
class Place(
    val lat: Double,
    val lng: Double,
    val time: String,
    val date: String,
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun fromJson(json: String): Place {
            return Gson().fromJson(json, Place::class.java)
        }
    }
}

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var httpClient: HttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the HttpClient with JSON and Logging features
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
            install(Logging) {
                level = LogLevel.ALL
            }
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    private fun sendUserLocationToServer(place: Place) {
        GlobalScope.launch(Dispatchers.IO) {
            val url = URLBuilder("http://your-server-endpoint.com/api/locations")
            val response = httpClient.post<Unit> {
                url.takeFrom(url)
                contentType(ContentType.Application.Json)
                body = place
            }

            if (response.status.isSuccess()) {
                // Location sent successfully
                runOnUiThread {
                    Toast.makeText(this@MapsActivity, "Location sent to server", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Error occurred while sending location
                runOnUiThread {
                    Toast.makeText(this@MapsActivity, "Failed to send location", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
                /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
         val superUserLocation = LatLng(superUserLat, superUserLng)
          val userLocation = LatLng(userLat, userLng)
        // Add a marker in Sydney and move the camera

                    mMap.addPolyline(
                        PolylineOptions()
                            .add(superUserLocation, userLocation)
                            .width(5f)
                            .color(Color.RED)
                    )
        for(place in Places.places){
                println("Current Date is: ${place.date}")
                println("Current Time is: ${place.time}")
                println("Current lat is: ${place.lat}")
                println("Current lng is: ${place.lng}")

            addMarker(place.lat,place.lng,place.time)
        }

    }
    private fun addMarker(lat:Double, long:Double, title:String){
        mMap.addMarker(MarkerOptions().position(LatLng(lat,long)).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat,long)))
    }
}