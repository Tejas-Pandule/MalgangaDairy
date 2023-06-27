package com.tejas.malgangadairy.Activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.tejas.malgangadairy.Fragment.CartFragment
import com.tejas.malgangadairy.MainActivity
import com.tejas.malgangadairy.R
import com.tejas.malgangadairy.roomdb.AppDatabase
import com.tejas.malgangadairy.roomdb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject


class CheckoutActivity : AppCompatActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        //Razorpay Integration
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_9LULgOYfnMW9ar")

        val price  = intent.getStringExtra("totalCost")

        try {
            val options = JSONObject()
            options.put("name", "Malganga Dairy")
            options.put("description", "Reference No. #123456")
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg")
            options.put("theme.color", "#1A033B")
            options.put("currency", "INR")
            options.put("amount", (price!!.toInt()*100))//pass amount in currency subunits
            options.put("prefill.email", "tejaspandule9036@gmail.com")
            options.put("prefill.contact", "7796709036")
            checkout.open(this, options)
        } catch (e: Exception) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_SHORT).show()

        uploadData()
    }

    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")

        for(currentId in id!!){
            fetchData(currentId)
        }
    }

    private fun fetchData(productId: String?) {

        val dao = AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("products")
            .document(productId!!).get().addOnSuccessListener {

                lifecycleScope.launch(Dispatchers.IO){
                    dao.deleteProduct(ProductModel(productId))
                }





                saveData(it.getString("productName"),
                    it.getString("productSp"),
                    it.getString("productCoverImg"),
                    productId
                )

            }
            .addOnFailureListener {

            }
    }

    private fun saveData(name: String?, price: String?,coverImg:String?, productId: String) {

        val preferences = this.getSharedPreferences("user", MODE_PRIVATE)
        val userAddress = this.getSharedPreferences("address", MODE_PRIVATE)

        val data = hashMapOf<String,Any>()
        data["name"] =name!!
        data["price"]= price!!
        data["coverImg"]= coverImg!!
        data["productId"] =productId
        data["userId"]=preferences.getString("number","7796709036")!!
        data["status"] = "Ordered"
        data["address"] = userAddress.getString("area","Shriramnagar")!! +", " + userAddress.getString("city","supa") +","+ userAddress.getString("pincode","414301")

        val firestore = Firebase.firestore.collection("allOrders")
        val key = firestore.document().id
        data["orderId"] =key


        firestore.document(key).set(data).addOnSuccessListener {
            Toast.makeText(this, "Order Placed", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
            .addOnFailureListener {
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }


    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()

        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}