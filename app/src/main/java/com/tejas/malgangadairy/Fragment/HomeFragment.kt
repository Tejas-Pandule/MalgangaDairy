package com.tejas.malgangadairy.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tejas.malgangadairy.Adaptor.CategoryAdaptor
import com.tejas.malgangadairy.Adaptor.ProductAdaptor
import com.tejas.malgangadairy.Model.Add_Product_Model
import com.tejas.malgangadairy.Model.Modelclass
import com.tejas.malgangadairy.R
import com.tejas.malgangadairy.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
   private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)

        binding.pgBar.isVisible = true
        binding.Loading.isVisible = true

        getCategories()
//        getSlider()           // Currently no need of slider.
        getProducts()

        val preference = requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)

        if (preference.getBoolean("isCart",false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)



        return binding.root
    }




    private fun getProducts() {
        val list = ArrayList<Add_Product_Model>()
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                // Loading Bar Mechanism.
                binding.pgBar.isVisible = false
                binding.Loading.isVisible = false
                binding.homeLayout.isVisible = true
                list.clear()

                for (doc in it.documents){
                    val data = doc.toObject(Add_Product_Model::class.java)
                    list.add(data!!)
                }

                binding.productRecyclerView.adapter = ProductAdaptor(requireContext(),list)
            }
            .addOnFailureListener {

            }

    }

    private fun getCategories() {
        val list = ArrayList<Modelclass>()
        Firebase.firestore.collection("category")
            .get().addOnSuccessListener {

                // Loading Bar Mechanism.
                binding.pgBar.isVisible = false
                binding.Loading.isVisible = false
                binding.homeLayout.isVisible = true
                list.clear()
                for (doc in it.documents){
                    val data = doc.toObject(Modelclass::class.java)
                    list.add(data!!)
                }

                binding.categoryRecyclerView.adapter = CategoryAdaptor(requireContext(),list)
            }
    }

}