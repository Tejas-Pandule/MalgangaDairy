package com.tejas.malgangadairy.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tejas.malgangadairy.Adaptor.AllOrderAdaptor
import com.tejas.malgangadairy.Model.AllOrderModel
import com.tejas.malgangadairy.R
import com.tejas.malgangadairy.databinding.ActivityMyOrdersBinding

class MyOrdersActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMyOrdersBinding
    private lateinit var list:ArrayList<AllOrderModel>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "My Orders"
        list = ArrayList()

        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)

        Firebase.firestore.collection("allOrders").whereEqualTo("userId",preferences.getString("number","0123456789"))
            .get().addOnSuccessListener {
            list.clear()

            for (doc in it){
                val data = doc.toObject(AllOrderModel::class.java)
                list.add(data)

            }

            binding.RecyclerView.adapter = AllOrderAdaptor(list,this)
        }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }
}