package com.example.soc_beta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        ref = FirebaseDatabase.getInstance().getReference("USERS")

        button3.setOnClickListener {
          val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        button4.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        button8.setOnClickListener {
            savedata()
        }
    }

    private fun savedata() {
        val nama = inputNama.text.toString()
        val email = inputEmail.text.toString()
        val nohp = inputNoHp.text.toString()

        val user = Users(nama,email,nohp)
        val userId = ref.push().key.toString()

        ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Tersimpan Ke Database", Toast.LENGTH_SHORT).show()
            inputNama.setText("")
            inputEmail.setText("")
            inputNoHp.setText("")
        }
    }
}
