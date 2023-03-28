package com.example.rokkha

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.telephony.SmsManager
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import java.util.*
import com.google.firebase.auth.FirebaseAuth



class Alert : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var timer: Timer? = null
    private var isSendingLocation = false
    private lateinit var  alertbutton: Button
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var user: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> startActivity(Intent(this, Alert::class.java))
                R.id.contacts -> startActivity(Intent(this, Helper::class.java))
                R.id.logout -> logout()
            }
            true
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        alertbutton = findViewById<Button>(R.id.buttonalert)
        alertbutton.setOnClickListener {
            if (!isSendingLocation) {
                startLocationSending()
            } else {
                stopLocationSending()
            }
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun startLocationSending() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
            return
        }

        isSendingLocation = true
        alertbutton.text = "Stop" // change button text to "Stop"

        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (ActivityCompat.checkSelfPermission(
                        this@Alert,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        this@Alert,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return
                }
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            sendLocationToContacts(it)
                        }
                    }
            }
        }, 0, 10000)
    }

    private fun stopLocationSending() {
        isSendingLocation = false
        alertbutton.text = "Alert" // change button text back to "Alert"

        timer?.cancel() // stop the location sending timer
    }
    private fun sendLocationToContacts(location: Location) {
        val message =
            "I am in danger!! Here's my location: https://www.google.com/maps/search/?api=1&query=${location.latitude},${location.longitude}"
        val dataBase: SqliteDatabase = SqliteDatabase(this)
        val allContacts = dataBase.listContacts()
        val smsMgr: SmsManager = SmsManager.getDefault()
        for (contact in allContacts) {
            try {
                smsMgr.sendTextMessage(
                    "0" + contact.phno,
                    null,
                    message,
                    null,
                    null
                )
                Handler(mainLooper).post {
                    Toast.makeText(
                        this,
                        "Alert sent to " + contact.name,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Handler(mainLooper).post {
                    Toast.makeText(
                        this,
                        "Message sent failed " + contact.name,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode) {
                MY_PERMISSIONS_REQUEST_LOCATION -> {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        startLocationSending()
                    }
                }
            }
        }
    private fun logout(){
        user = FirebaseAuth.getInstance()
        user.signOut()
        startActivity(
            Intent(this,MainActivity::class.java)
        )
        finish()

    }

}