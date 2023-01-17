package com.example.autoattendance

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.example.autoattendance.databinding.ActivityMainBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase




class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.loginBtn.setOnClickListener {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
            if (binding.enrolmentNumberEdit.text.toString().isNullOrEmpty() || binding.passwordEdittext.text.toString() .isNullOrEmpty())
                Toast.makeText(this, "Email Address or Password is not provided", Toast.LENGTH_LONG).show()

            else {
                auth.createUserWithEmailAndPassword(
                    binding.enrolmentNumberEdit.text.toString(),
                    binding.passwordEdittext.text.toString()
                )
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
                                        val intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                                    } else {
                                        Toast.makeText(
                                            this,
                                            "Sign Up Failed",
                                            Toast.LENGTH_LONG
                                        ).show()

                                    }
                                }
                        }
                    }





        binding.navToSignupStudentBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivityStudent::class.java)
            startActivity(intent)
        }

        binding.navToSignupAdminBtn.setOnClickListener {
            val intent = Intent(this, SignUpActivityAdmin::class.java)
            startActivity(intent)
        }

    }




}
