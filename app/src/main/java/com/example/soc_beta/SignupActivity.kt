package com.example.soc_beta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_signup.*

class SignupActivity : AppCompatActivity() {

    lateinit var ref: DatabaseReference
    var sudahAda = 0
    var berapaKali = 0
    var fieldCheck = "email"
    var fieldCheckTxt = "email"
    var email = ""
    var nohp = ""
    var noktp = ""
    var nosim = ""
    var nama = ""
    var alamat = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        ref = FirebaseDatabase.getInstance().getReference("USERS")


        button8.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            nama  = inputNama.text.toString()
            email = inputEmail.text.toString()
            nohp = inputNoHp.text.toString()
            noktp = inputNoKTP.text.toString()
            nosim = inputNoSIM.text.toString()
            alamat = inputAlamat.text.toString()

            if (nama.equals("") || email.equals("") || nohp.equals("") || noktp.equals("") || nosim.equals("") || alamat.equals("")){
                Toast.makeText(
                    this@SignupActivity,
                    "Nama/Email/NoHp/NoKTP/NoSIM/Alamat Wajib Diisi",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (!email.contains ("@") || !email.contains (".")){
                Toast.makeText(
                    this@SignupActivity,
                    "Alamat Email tidak Valid",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            fieldCheck = email
            fieldCheckTxt = "email"
            checkData()
        }
    }

    private fun checkData(){
        sudahAda = 0
        val usersRef = ref.orderByChild(fieldCheckTxt).equalTo(fieldCheck)
        usersRef.addListenerForSingleValueEvent(valueEventListener)
    }

    var valueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (ds in dataSnapshot.children)
                sudahAda = 1

            if (sudahAda == 1) {
                Toast.makeText(
                    this@SignupActivity,
                    fieldCheckTxt + " (" + fieldCheck + ") Sudah Pernah Digunakan",
                    Toast.LENGTH_SHORT
                ).show()
                return
            } else {
                sudahAda = 0
                if (fieldCheckTxt == "email"){
                    fieldCheck = nohp
                    fieldCheckTxt = "nohp"
                    checkData()
                }
                else if (fieldCheckTxt == "nohp"){
                    fieldCheck = noktp
                    fieldCheckTxt = "noktp"
                    checkData()
                }
                else if (fieldCheckTxt == "noktp"){
                    fieldCheck = nosim
                    fieldCheckTxt = "nosim"
                    checkData()
                }
                else
                    savedata()


            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            //Log.d(TAG, databaseError.getMessage()) //Don't ignore errors!
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

            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            startActivity(intent)


        }
    }

}
