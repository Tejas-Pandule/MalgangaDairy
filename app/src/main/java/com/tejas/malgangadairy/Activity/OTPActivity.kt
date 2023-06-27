package com.tejas.malgangadairy.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.tejas.malgangadairy.MainActivity
import com.tejas.malgangadairy.R
import com.tejas.malgangadairy.databinding.ActivityOtpactivityBinding

class OTPActivity : AppCompatActivity() {
    private lateinit var binding:ActivityOtpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        
        binding.button.setOnClickListener { 
            if (binding.userOtp.text!!.isEmpty()) {
                Toast.makeText(this, "Please provide OTP", Toast.LENGTH_SHORT).show()
            }
            else{
                verifyUser(binding.userOtp.text.toString())
            }
        }


    }

    private lateinit var builder:AlertDialog
    private fun verifyUser(OTP: String) {
        builder = AlertDialog.Builder(this)
            .setTitle("Loading...!")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()

        val credential = PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!, OTP)

        signInWithPhoneAuthCredential(credential)

    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preferences.edit()
                    editor.putString("number",intent.getStringExtra("number")!!)
                    editor.apply()


                    builder.dismiss()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()


                } else {
                    Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
    }
}