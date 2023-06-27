package com.tejas.malgangadairy.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tejas.malgangadairy.databinding.ActivityAddressBinding

class AddressActivity : AppCompatActivity() {
    private lateinit var binding:ActivityAddressBinding
    private lateinit var preferences:SharedPreferences
    private lateinit var totalCost:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost = intent.getStringExtra("totalCost")!!

        loadUserInfo()

        binding.proceed.setOnClickListener {
            validateData(
                binding.userArea.text.toString(),
                binding.userCity.text.toString(),
                binding.userPincode.text.toString()
            )
        }
    }

    private fun validateData(
        area: String,
        city: String,
        pincode: String
    ) {
        if (area.isEmpty()||city.isEmpty()||pincode.isEmpty()){
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
        }else{
            //Name and Number already present in database
            storeData(area,city,pincode)
        }
    }

    private fun storeData(
        area: String,
        city: String,
        pincode: String
    ) {

        val preferences = this.getSharedPreferences("address", MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("area",area)
        editor.putString("city",city)
        editor.putString("pincode",pincode)
        editor.apply()


        val data = hashMapOf<String,Any>()

        data["area"] = area
        data["city"] = city
        data["pincode"] = pincode

        Firebase.firestore.collection("users")
            .document(preferences.getString("number","7796709036")!!)
            .update(data)
            .addOnSuccessListener {

                val b = Bundle()
                b.putStringArrayList("productIds",intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost",totalCost)

                val intent = Intent(this, CheckoutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadUserInfo() {


        Firebase.firestore.collection("users")
            .document(preferences.getString("number","7796709036")!!)
            .get().addOnSuccessListener {

                binding.userName.setText(it.getString("userName"))
                binding.userNumber.setText(it.getString("userPhoneNumber"))
                binding.userArea.setText(it.getString("area"))
                binding.userCity.setText(it.getString("city"))
                binding.userPincode.setText(it.getString("pincode"))

            }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }
}