package com.example.soc_beta.ui.home
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.soc_beta.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.nav_header_beri_tumpangan.*
import java.text.SimpleDateFormat
import java.util.*

    class DateTime : AppCompatActivity() {

        lateinit var ref: DatabaseReference
        private lateinit var homeViewModel: HomeViewModel
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

            mPickDateBtn.setOnClickListener {

                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, _, dayOfMonth ->
                    // Display Selected date in TextView
                    textViewDate.setText("" + dayOfMonth + " " + month + ", " + year)
                }, year, month, day)
                dpd.show()

            }
            mPickTimeBtn.setOnClickListener {
                val cal = Calendar.getInstance()
                val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                    cal.set(Calendar.HOUR_OF_DAY, hour)
                    cal.set(Calendar.MINUTE, minute)
                    textViewTime.text = SimpleDateFormat("HH:mm").format(cal.time)
                }
                TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
            }

            ref = FirebaseDatabase.getInstance().getReference("GO")

            buttonGo.setOnClickListener {
                savedata()}

            homeViewModel.text.observe(this, Observer {
                textViewDate.text = it
            })

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