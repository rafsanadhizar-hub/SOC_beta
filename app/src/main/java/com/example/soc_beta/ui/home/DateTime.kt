package com.example.soc_beta.ui.home
import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.soc_beta.R
import java.util.*

    class DateTime : AppCompatActivity() {

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            val mPickTimeBtn = findViewById<Button>(R.id.inputTanggalBerangkat)
            val textView     = findViewById<TextView>(R.id.textTanggal)

            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            mPickTimeBtn.setOnClickListener {

                val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    textView.setText("" + dayOfMonth + " " + month + ", " + year)
                }, year, month, day)
                dpd.show()

            }

        }
    }