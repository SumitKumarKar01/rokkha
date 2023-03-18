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
import android.telephony.SmsManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices






class alert : AppCompatActivity() {
    private val MY_PERMISSIONS_REQUEST_LOCATION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val alertbutton = findViewById<Button>(R.id.buttonalert)
        alertbutton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), MY_PERMISSIONS_REQUEST_LOCATION)
            } else {
                getLocationAndSend()
            }
        }
        val contact = findViewById<Button>(R.id.addcontact)
        contact.setOnClickListener {
            val intent = Intent(this, Helper::class.java)
            startActivity(intent)
        }
    }

    private fun getLocationAndSend() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    /*val message = "I am in danger!! Here's my location: https://www.google.com/maps/search/?api=1&query=${it.latitude},${it.longitude}"
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("smsto:${+8801990188461}") // Replace phoneNumber with the phone number of your contact
                        putExtra("sms_body", message)
                    }
                    startActivity(intent)*/
                    val message = "I am in danger!! Here's my location: https://www.google.com/maps/search/?api=1&query=${it.latitude},${it.longitude}"

                    var dataBase: SqliteDatabase = SqliteDatabase(this)
                    val allContacts = dataBase.listContacts()
                    var smsMgr: SmsManager? = null
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                       smsMgr = this.getSystemService<SmsManager>(SmsManager::class.java)
                    } else {
                        smsMgr = SmsManager.getDefault()
                    }
                    for (contact in allContacts){
                      /*  try {
                        val sent = sms.sendTextMessage("0"+contact.phno, "01732073478", message, null, null)
                        Toast.makeText(this, "Alert sent to "+contact.phno, Toast.LENGTH_SHORT).show();
                    } catch (e: Exception) {
                            Toast.makeText(this, "Message sent failed "+contact.name, Toast.LENGTH_SHORT).show();
                     }*/
                        try{
                        var SENT = "SMS_SENT"
                        val sentPI = PendingIntent.getBroadcast(this, 0, Intent(SENT), 0)
                        registerReceiver(object : BroadcastReceiver() {
                            override fun onReceive(arg0: Context?, arg1: Intent?) {
                                val resultCode = resultCode
                                when (resultCode) {
                                    RESULT_OK -> Toast.makeText(
                                        baseContext,
                                        "Alert sent to "+contact.name,
                                        Toast.LENGTH_LONG
                                    ).show()
                                    else -> Toast.makeText(
                                        baseContext,
                                        "Alert sent to "+contact.name + " error: "+resultCode,
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }, IntentFilter(SENT))

                        smsMgr.sendTextMessage("0"+contact.phno, null, message, sentPI, null)

                        } catch (e:Exception) {
                            Toast.makeText(this, "$e!\nFailed to send SMS", Toast.LENGTH_LONG).show()
                            e.printStackTrace()
                        }
                }
            }
    }
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocationAndSend()
                }
            }
        }
    }






}}