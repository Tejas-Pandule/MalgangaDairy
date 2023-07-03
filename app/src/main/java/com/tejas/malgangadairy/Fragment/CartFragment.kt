package com.tejas.malgangadairy.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.tejas.malgangadairy.Activity.AddressActivity
import com.tejas.malgangadairy.Adaptor.CartAdaptor
import com.tejas.malgangadairy.MainActivity
import com.tejas.malgangadairy.R
import com.tejas.malgangadairy.databinding.FragmentCartBinding
import com.tejas.malgangadairy.roomdb.AppDatabase
import com.tejas.malgangadairy.roomdb.ProductModel


class CartFragment : Fragment() {
    private lateinit var binding:FragmentCartBinding
    private lateinit var list:ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(layoutInflater)


        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart",false)
        editor.apply()


        val dao = AppDatabase.getInstance(requireContext()).productDao()
        list = ArrayList()

        dao.getAllProducts().observe(requireActivity()){
            binding.cartRecycler.adapter = CartAdaptor(requireContext(),it)

            //this is list for sending productid
            list.clear()
            for (data in it){
                list.add(data.productId)
            }

            totalCost(it)

        }




        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun totalCost(data: List<ProductModel>?) {
        var total = 0.0
        for (item in data!!){
            total += item.productSp!!.toFloat()*item.quantity!!.toFloat()
        }

        binding.textView.text = "Total items in cart: ${data.size}"
        binding.textView1.text = "Total Cost: ₹${total}"


        binding.btnCheckout.setOnClickListener {
            val intent = Intent(context, AddressActivity::class.java)

            val b = Bundle()
            b.putStringArrayList("productIds",list)
            b.putString("totalCost",total.toString())
            intent.putExtras(b)
            startActivity(intent)
        }



    }


}