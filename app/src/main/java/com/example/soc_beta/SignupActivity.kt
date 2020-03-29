package com.example.soc_beta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        ref = FirebaseDatabase.getInstance().getReference("USERS")

        button8.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            savedata()
        }
    }

    private fun savedata() {
        val nama = inputNama.text.toString()
        val email = inputEmail.text.toString()
        val nohp = inputNoHp.text.toString()
        val password = inputPwSignup.text.toString()
        val konfpassword = inputKonfPwSignup.text.toString()
        val tempatlahir = inputTempatLahir.text.toString()
        val tanggallahir = inputTglLahir.text.toString()
        val noktp = inputNoKTP.text.toString()
        val nosim = inputNoSIM.text.toString()
        val tanggalkadaluarsa = inputTglKadaluarsa.text.toString()
        val alamat = inputAlamat.text.toString()
        val kodepos = inputKodePos.text.toString()
        val merk = inputMerk.text.toString()
        val plat = inputPlat.text.toString()
        val kapasitas = inputKapasitas.text.toString()

        val user = Users(nama,email,nohp,password, konfpassword,tempatlahir,tanggallahir,noktp,nosim,tanggalkadaluarsa,alamat,kodepos,merk,plat,kapasitas)
        val userId = this.ref.push().key.toString()

        this.ref.child(userId).setValue(user).addOnCompleteListener {
            Toast.makeText(this, "Tersimpan Ke Database", Toast.LENGTH_SHORT).show()
            inputNama.setText("")
            inputEmail.setText("")
            inputNoHp.setText("")
            inputPwSignup.setText("")
            inputKonfPwSignup.setText("")
            inputTempatLahir.setText("")
            inputTglLahir.setText("")
            inputNoKTP.setText("")
            inputNoSIM.setText("")
            inputTglKadaluarsa.setText("")
            inputAlamat.setText("")
            inputKodePos.setText("")
            inputMerk.setText("")
            inputPlat.setText("")
            inputKapasitas.setText("")
        }
    }
}
