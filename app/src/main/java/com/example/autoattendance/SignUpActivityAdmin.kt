package com.example.autoattendance

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import com.example.autoattendance.databinding.ActivitySignUpAdminBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.inflate
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.WindowCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.jar.Attributes

class SignUpActivityAdmin : AppCompatActivity() {
    private lateinit var binding: SignUpActivityAdmin
    private lateinit var auth: FirebaseAuth

    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up_admin)

        auth = FirebaseAuth.getInstance()
        var verify_btn = findViewById<Button>(R.id.verify_btn)
        var id_edittext = findViewById<EditText>(R.id.id_edittext_2)
        var email_edittext = findViewById<EditText>(R.id.email_edittext_2)
        var password_edittext = findViewById<EditText>(R.id.pasword_edittext_2)


        val db = DBHelperAdminDetail(this, null)

            verify_btn.setOnClickListener {
                var id = id_edittext.text.toString()
                var email = email_edittext.text.toString()
                var password = password_edittext.text.toString()
                if (id.isNullOrEmpty() || email.isNullOrEmpty() || password.isNullOrEmpty()) {
                    Toast.makeText(
                        this,
                        "Please fill all the detail before proceeed",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this, "Hello1", Toast.LENGTH_LONG).show()
                    val cursor = db.readUser(id_edittext.text.toString())
//                    cursor!!.moveToFirst()
//                    val temp =
//                        (cursor.getString(cursor.getColumnIndex(DBHelperAdminDetail.EMAIL_COL)) + "\n")
                    Toast.makeText(this, "Hello2", Toast.LENGTH_LONG).show()
                    if (cursor == null) {
                        db.addId(id, email)
                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    auth.currentUser?.sendEmailVerification()
                                        ?.addOnCompleteListener { task ->
                                            if (task.isSuccessful) {
                                                Toast.makeText(
                                                    this,
                                                    "Sign Up successful. Email verification is emailed",
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
                                        }
                                }
                            }
                    } else {
                        val user = auth.currentUser
                        if (user != null) {
                            if(user.isEmailVerified){
                                val intent = Intent(this, RegisterActivityAdmin::class.java)
                                startActivity(intent)

                            }

                            else{
                                Toast.makeText(this, "Please verify your email before proceed", Toast.LENGTH_LONG).show()
                            }
                        }
                    }


                }

            }


    }


}