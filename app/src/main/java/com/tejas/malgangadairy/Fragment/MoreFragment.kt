package com.tejas.malgangadairy.Fragment



import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tejas.malgangadairy.Activity.HelpCenterActivity
import com.tejas.malgangadairy.Activity.LoginActivity
import com.tejas.malgangadairy.Activity.MyOrdersActivity
import com.tejas.malgangadairy.Activity.NotificationActivity
import com.tejas.malgangadairy.R
import com.tejas.malgangadairy.databinding.FragmentMoreBinding
import com.tejas.malgangadairy.roomdb.AppDatabase
import com.tejas.malgangadairy.roomdb.ProductModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MoreFragment : Fragment() {
    private  lateinit var binding:FragmentMoreBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)

        val auth = FirebaseAuth.getInstance()


        val preferences = requireActivity().getSharedPreferences("user",MODE_PRIVATE)


      //Loading User's Name from Firebase
       Firebase.firestore.collection("users")
           .document(preferences.getString("number","0123456789")!!)
           .get().addOnSuccessListener {
               binding.txtUserName.text = it.getString("userName")
           }
           .addOnFailureListener {

           }


        //Log Out Button
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Log Out")
        builder.setMessage("Are you sure, You want to Logout?")
        builder.setIcon(R.drawable.logout)
        builder.create()
        builder.setPositiveButton("Yes"){ builder, _ ->
            auth.signOut()
            startActivity(Intent(requireContext(),LoginActivity::class.java))
        }
        builder.setNegativeButton("No"){ builder, _ ->
            builder.dismiss()
        }

        binding.button5.setOnClickListener {
            builder.show()

        }

        binding.materialCardView2.setOnClickListener {
            val intent = Intent(requireContext(),MyOrdersActivity::class.java)
            startActivity(intent)
        }

        binding.materialCardView3.setOnClickListener {
            val intent = Intent(requireContext(),NotificationActivity::class.java)
            startActivity(intent)
        }

        binding.materialCardView4.setOnClickListener {
            val intent = Intent(requireContext(),HelpCenterActivity::class.java)
            startActivity(intent)
        }




        return binding.root
    }





}