package com.testproject.biketrack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.Socket
import android.preference.PreferenceManager

class MapActivity : AppCompatActivity() {
    private lateinit var map: MapView
    private lateinit var marker: Marker
    private val scope = CoroutineScope(Dispatchers.IO + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(applicationContext, PreferenceManager.getDefaultSharedPreferences(applicationContext))
        setContentView(R.layout.activity_map)

        map = findViewById(R.id.map)
        map.setTileSource(TileSourceFactory.MAPNIK)
        map.setMultiTouchControls(true)

        val startPoint = GeoPoint(14.5995, 120.9842) // Manila default
        map.controller.setZoom(15.0)
        map.controller.setCenter(startPoint)

        marker = Marker(map)
        marker.position = startPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = "Tracking"
        map.overlays.add(marker)

        connectToServer("192.168.151.1", 8080)
    }

    private fun connectToServer(ip: String, port: Int) {
        scope.launch {
            try {
                val socket = Socket(ip, port)
                val reader = BufferedReader(InputStreamReader(socket.getInputStream()))
                Log.d("MapActivity", "Connected to server")

                while (true) {
                    val data = reader.readLine()
                    if (data != null) {
                        Log.d("MapActivity", "Received: $data")

                        val parts = data.split(",")
                        Log.d("MapActivity", "Parts: ${parts.joinToString()}")

                        if (parts.size >= 2) {
                            val lat = parts[0].toDoubleOrNull()
                            val lon = parts[1].toDoubleOrNull()
                            if (lat != null && lon != null) {
                                updateMapLocation(lat, lon)
                            } else {
                                Log.e("MapActivity", "Invalid lat/lon values")
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("MapActivity", "Socket error: ${e.message}")
            }
        }
    }

    private fun updateMapLocation(latitude: Double, longitude: Double) {
        Log.d("MapActivity", "Updating map to: $latitude, $longitude")
        runOnUiThread {
            val newPoint = GeoPoint(latitude, longitude)
            marker.position = newPoint
            map.controller.setCenter(newPoint)
            map.invalidate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
