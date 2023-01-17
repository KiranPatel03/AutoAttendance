package com.example.autoattendance

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivityAdmin : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_admin)

        auth = FirebaseAuth.getInstance()
        val register_btn = findViewById<Button>(R.id.register_btn)
        val email_edittext = findViewById<EditText>(R.id.email_edittext_2)
        val password_edittext = findViewById<EditText>(R.id.pasword_edittext_2)

        register_btn.setOnClickListener {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            if (email_edittext.text.toString()
                    .isNullOrEmpty() || password_edittext.text.toString().isNullOrEmpty())
                Toast.makeText(this, "Email Address or Password is not provided", Toast.LENGTH_LONG)
                    .show()
            else {
                auth.signInWithEmailAndPassword(
                    email_edittext.text.toString(),
                    password_edittext.text.toString()
                )
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Sign IN successful.",
                                Toast.LENGTH_LONG
                            ).show()
                            val user = auth.currentUser
                            updateUI(user, email_edittext.text.toString())


                        } else {
                            Toast.makeText(
                                this,
                                "Sign IN Failed",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                    }
            }
        }
    }

    private fun updateUI(currentUser: FirebaseUser?, emailAdd: String) {
        if(currentUser!=null){
            if(currentUser.isEmailVerified) {
                val db=DBHelperAdminDetail(this, null)
                val id=findViewById<EditText>(R.id.id_edittext_2).toString()
                val email=findViewById<EditText>(R.id.email_edittext_2).toString()
                db.addId(id,email)
                finish()
            }
        }
    }
}