package com.tejas.malgangadairy.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.tejas.malgangadairy.databinding.ActivityLoginBinding
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.button2.setOnClickListener {
            startActivity(Intent(this,RegisterActivity::class.java))
            finish()
        }
        
        binding.button.setOnClickListener { 
            if (binding.userNumber.text!!.isEmpty()){
                Toast.makeText(this, "Please provide number", Toast.LENGTH_SHORT).show()
            }

            else{
                sendOTP(binding.userNumber.text.toString())

            }
        }
    }



   private lateinit var builder:AlertDialog
    private fun sendOTP(number: String) {

        builder = AlertDialog.Builder(this)
            .setTitle("OTP Verification")
            .setMessage("OTP is sent to Your Mobile Number.!")
            .setCancelable(false)
            .create()
        builder.show()

        val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$number") // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
             builder.dismiss()
        }

        override fun onVerificationFailed(e: FirebaseException) {


        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            builder.dismiss()
            val intent = Intent(this@LoginActivity,OTPActivity::class.java)
            intent.putExtra("verificationId",verificationId)
            intent.putExtra("number",binding.userNumber.text.toString())
            startActivity(intent)
            finish()
        }
    }
}