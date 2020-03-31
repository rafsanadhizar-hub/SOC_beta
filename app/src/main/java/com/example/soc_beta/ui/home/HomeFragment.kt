package com.example.soc_beta.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.soc_beta.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    lateinit var ref: DatabaseReference
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)


        ref = FirebaseDatabase.getInstance().getReference("GO")

        root.buttonGo.setOnClickListener {
            savedata()}

        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }

    private fun savedata() {
        val kursi = inputKursiTersedia.text.toString()

        val goes = Go(kursi)
        val goId = this.ref.push().key.toString()

        Log.d("ardi", "sampe sini")

        this.ref.child(goId).setValue(goes).addOnCompleteListener {
            inputKursiTersedia.setText("")
        }
    }
}
