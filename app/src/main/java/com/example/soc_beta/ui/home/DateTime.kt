package com.example.soc_beta.ui.home
import android.Manifest
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.soc_beta.R
import com.example.soc_beta.Wander
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_beri_tumpangan.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.nav_header_beri_tumpangan.*
import java.text.SimpleDateFormat
import java.util.*

class DateTime : AppCompatActivity(), OnMapReadyCallback {

    lateinit var ref: DatabaseReference
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var map: GoogleMap
    private lateinit var posisi: String
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val mPickDateBtn = findViewById<Button>(R.id.inputTanggalBerangkat)
        val textViewDate     = findViewById<TextView>(R.id.textTanggal)
        val mPickTimeBtn = findViewById<Button>(R.id.inputWaktuBerangkat)
        val textViewTime     = findViewById<TextView>(R.id.textWaktu)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val mPickBerangkatBtn = findViewById<Button>(R.id.buttonLokasiAwal)

        homeViewModel.text.observe(this, Observer {
            textViewDate.text = it
        })

        setContentView(R.layout.activity_beri_tumpangan)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        val txtViewBerangkat = findViewById<TextView>(R.id.textBerangkat)
//        val mPickAkhirBtn = findViewById<Button>(R.id.buttonLokasiAkhir)
//        val txtViewAkhir = findViewById<TextView>(R.id.textTujuan)

//            buttonLokasiAwal.setOnClickListener {
//                val intent = Intent(this, Wander::class.java)
//                startActivity(intent)
//            }
//
//            buttonLokasiAkhir.setOnClickListener {
//                val intent = Intent(this, Wander::class.java)
//                startActivity(intent)
//            }

        mPickDateBtn.setOnClickListener {
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                // Nampilin ke textview
                textViewDate.setText("" + dayOfMonth + " " + month + ", " + year)
            }, year, month, day)
            dpd.show()

            Log.d("aku", "listener jalan")
        }

        mPickTimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                textViewTime.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        mPickBerangkatBtn.setOnClickListener{
            setContentView(R.layout.activity_wander)
       // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }

        ref = FirebaseDatabase.getInstance().getReference("GO")

//        buttonGo.setOnClickListener {
//            savedata()
//        }
        /*
//        setContentView(R.layout.activity_wander)
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)

         */
    }
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val latitude = -6.277848
        val longitude = 106.985862

        val homeLatLng = LatLng(latitude, longitude)
        val zoomLevel = 15f
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))
        map.addMarker(MarkerOptions().position(homeLatLng))
        setPoiClick(map)
        enableMyLocation()
        setMapLongClick(map)
    }
    private fun setMapLongClick(map:GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
            )
        }
    }
    private fun setPoiClick(map: GoogleMap) {
        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.position.latitude
            poiMarker.position.longitude
            textBerangkat.setText(poiMarker.position.toString())
            posisi = poiMarker.position.toString()
            poiMarker.showInfoWindow()

        }
//                Log.d("aku", poiMarker.position.latitude.toString())
    }

    private val REQUEST_LOCATION_PERMISSION = 1
    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.beri_tumpangan, menu)
        return true
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun savedata() {
        val kursi = inputKursiTersedia.text.toString()
        val tanggalberangkat = textTanggal.text.toString()
        val waktuberangkat = textWaktu.text.toString()
        val goes = Go(kursi, tanggalberangkat, waktuberangkat)
        val goId = this.ref.push().key.toString()

        this.ref.child(goId).setValue(goes).addOnCompleteListener {
            inputKursiTersedia.setText("")
            textTanggal.setText("")
            textWaktu.setText("")
        }
    }
}