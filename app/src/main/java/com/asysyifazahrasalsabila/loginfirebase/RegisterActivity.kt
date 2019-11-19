package com.asysyifazahrasalsabila.loginfirebase

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegis.setOnClickListener {
            val nama = regisNama.text.toString()
            val email = regisEmail.text.toString()
            val password = regisPassword.text.toString()

            if (nama.isEmpty()){
                Toast.makeText(this, "Please Insert Name!", Toast.LENGTH_SHORT).show()
                regisNama.requestFocus()
            }
            if (email.isEmpty()){
                Toast.makeText(this, "Please Insert Email!", Toast.LENGTH_SHORT).show()
                regisEmail.requestFocus()
            }
            if (password.isEmpty()){
                Toast.makeText(this, "Please Insert Password!", Toast.LENGTH_SHORT).show()
                regisPassword.requestFocus()
            }

            val progressDialog = ProgressDialog(this, R.style.Theme_MaterialComponents_Dialog)
            progressDialog.isIndeterminate = true
            progressDialog.setMessage("Creating User...")
            progressDialog.show()

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    progressDialog.hide()

                    if (it.isSuccessful){
                        val userId = FirebaseAuth.getInstance().currentUser!!.uid
                        val currentUser = FirebaseDatabase.getInstance().reference.child("Users").child(userId)
                        currentUser.child("Name").setValue(nama)

                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
