package com.example.soc_beta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_1.*


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_1)

        button5.setOnClickListener {
            val email = inputEmail2.text.toString()
            val password = inputPassword2.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Mohon Isi Email Dan Password Terlebih Dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            //test



            //FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                //.addOnCompleteListener{

                  //  if (!it.isSuccessful){ return@addOnCompleteListener
                    //    val intent = Intent (this, MainActivity::class.java)
                      //  startActivity(intent)
                    //}
                    //else
                      //  Toast.makeText(this, "Berhasil Login", Toast.LENGTH_SHORT).show()
                    //val intent = Intent (this, CariTumpanganActivity::class.java)
                    //startActivity(intent)
                //}
                //.addOnFailureListener{
                    //Log.d("Main", "Login Gagal: ${it.message}")
                    //Toast.makeText(this, "Email/Password Salah", Toast.LENGTH_SHORT).show()
                //}


            // val intent = Intent(this, CariTumpanganActivity::class.java)
            // start your next activity
            // startActivity(intent)
        }

        button3.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
    }
        button4.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
    }
    }
}
