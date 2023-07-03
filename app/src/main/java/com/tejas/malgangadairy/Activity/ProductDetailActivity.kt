package com.tejas.malgangadairy.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tejas.malgangadairy.MainActivity
import com.tejas.malgangadairy.databinding.ActivityProductDetailBinding
import com.tejas.malgangadairy.roomdb.AppDatabase
import com.tejas.malgangadairy.roomdb.ProductDao
import com.tejas.malgangadairy.roomdb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        getProductDetail(intent.getStringExtra("id"))







    }


    @SuppressLint("SetTextI18n")
    private fun getProductDetail(ProId: String?) {

        Firebase.firestore.collection("products")
            .document(ProId!!).get().addOnSuccessListener {
                val list = it.get("productImages")as ArrayList<String>
                val name = it.getString("productName")
                val productSp= it.getString("productSp")
                val productDescription= it.getString("productDescription")


                if (name=="Cow Milk"||name=="Buffalo Milk"){
                    binding.imageView6.isVisible = true
                    binding.textView13.isVisible = true

                }
                binding.proQuantity.hint = "Enter amount of $name"

                binding.textView3.text = name
                binding.textView4.text = "₹$productSp"
                binding.textView5.text = productDescription
                val slideList = ArrayList<SlideModel>()
                for (data in list){
                    slideList.add(SlideModel(data, ScaleTypes.CENTER_INSIDE))

                }
                binding.imageSlider.setImageList(slideList)


                cartAction(ProId,name,productSp,it.getString("productCoverImg"))


            }.addOnFailureListener {
                Toast.makeText(this, "Error While loading Data", Toast.LENGTH_SHORT).show()
            }

    }


    @SuppressLint("SetTextI18n")
    private fun cartAction(proId: String, name: String?, productSp: String?, coverImg: String?) {

        val productDao = AppDatabase.getInstance(this).productDao()


        if (productDao.isExit(proId) != null){
            binding.textView7.text = "Go to Cart"
            binding.proQuantity.isEnabled = false

            binding.textInputLayout2.setOnClickListener {
                Toast.makeText(
                    this,
                    "Please check previous order, Open cart..!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }else{

            binding.textView7.text = "Add To Cart"
        }


        binding.textView7.setOnClickListener {

            if (productDao.isExit(proId) != null){
                openCart()


            }else{
                AddToCart(productDao,proId,name,productSp,coverImg)
                Toast.makeText(this, "Item Added In Cart..!", Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun AddToCart(
        productDao: ProductDao,
        proId: String,
        name: String?,
        productSp: String?,
        coverImg: String?,


    ){


        val data = ProductModel(proId,name,productSp,coverImg,binding.proQuantity.text.toString())
        lifecycleScope.launch(Dispatchers.IO){
            productDao.insertProduct(data)
            binding.textView7.text = "Go to Cart"
            binding.proQuantity.text =null


        }
    }

    private fun openCart() {
        val preference = this.getSharedPreferences("info", MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart",true)
        editor.apply()


        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}