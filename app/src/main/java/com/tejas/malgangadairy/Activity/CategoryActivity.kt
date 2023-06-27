package com.tejas.malgangadairy.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tejas.malgangadairy.Adaptor.CategoryProductAdaptor
import com.tejas.malgangadairy.Model.Add_Product_Model
import com.tejas.malgangadairy.R

class CategoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)


        getProducts(intent.getStringExtra("cat"))
    }



    private fun getProducts(category: String?) {
        val list = ArrayList<Add_Product_Model>()
        Firebase.firestore.collection("products").whereEqualTo("productCategory",category)
            .get().addOnSuccessListener {
                list.clear()

                for (doc in it.documents){
                    val data = doc.toObject(Add_Product_Model::class.java)
                    list.add(data!!)
                }

                val recyclerView = findViewById<RecyclerView>(R.id.categoryProductRecyclerView)
                recyclerView.adapter = CategoryProductAdaptor(this,list)
            }
    }
}