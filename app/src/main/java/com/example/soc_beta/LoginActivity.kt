package com.example.soc_beta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.soc_beta.ui.home.DateTime
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        ref = FirebaseDatabase.getInstance().getReference("USERS")

        button5.setOnClickListener {
            val email = inputEmail2.text.toString()
            val password = inputPassword2.text.toString()
            var loginSuccess = 0


            val usersRef = ref.orderByChild("email").equalTo(email)
            val valueEventListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (ds in dataSnapshot.children) {
                        val dbpassword = ds.child("password").getValue(String::class.java)
                        if (dbpassword.equals(password))
                            loginSuccess = 1
                    }
                    if (loginSuccess == 1) {
                        val intent = Intent(this@LoginActivity, DateTime::class.java)
                        // start your next activity
                        startActivity(intent)


                    }
                    else
                        Toast.makeText(this@LoginActivity, "Username/Password Salah", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    //Log.d(TAG, databaseError.getMessage()) //Don't ignore errors!
                }


            }
            usersRef.addListenerForSingleValueEvent(valueEventListener)

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Mohon Isi Email Dan Password Terlebih Dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }





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
