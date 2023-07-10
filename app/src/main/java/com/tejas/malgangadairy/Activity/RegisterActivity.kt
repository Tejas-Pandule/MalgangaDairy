package com.tejas.malgangadairy.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tejas.malgangadairy.Model.UserModel
import com.tejas.malgangadairy.R
import com.tejas.malgangadairy.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.button2.setOnClickListener {
          openLogin()      //only intent passing
        }

        binding.button.setOnClickListener {
            validateUser()
        }
    }

    private fun validateUser() {
        if (binding.userNumber.text!!.isEmpty() || binding.userName.text!!.isEmpty()){
            Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
        }
        else{
            storeData()
        }
    }

    @SuppressLint("CommitPrefEdits")
    private fun storeData() {
        //Loading Dialog
        val builder = AlertDialog.Builder(this)
            .setTitle("Loading...")
            .setMessage("Please Wait")
            .setCancelable(false)
            .create()
        builder.show()

        // storing name and number in shared preference at time of new registration.
        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("name",binding.userName.text.toString())
        editor.apply()

        // adding name and number in firebase
        val data = UserModel(binding.userName.text.toString(),binding.userNumber.text.toString())

        Firebase.firestore.collection("users").document(binding.userNumber.text.toString())
            .set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "User Registered", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                builder.dismiss()
            }


    }

    private fun openLogin() {
        startActivity(Intent(this,LoginActivity::class.java))
        finish()
    }
}