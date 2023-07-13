package com.tejas.malgangadairy.Adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.tejas.malgangadairy.Model.AllOrderModel
import com.tejas.malgangadairy.databinding.AllOrderItemLayoutBinding


class AllOrderAdaptor(val list: ArrayList<AllOrderModel>,val context:Context): RecyclerView.Adapter<AllOrderAdaptor.ViewHolder>() {


     inner class ViewHolder(val binding:AllOrderItemLayoutBinding ):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AllOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.productName.text =list[position].name
        holder.binding.productPrice.text ="Price: "+"₹"+list[position].price

        if (list[position].name =="Cow Milk"||list[position].name =="Buffalo Milk"){
            holder.binding.proQuantity.text ="Quantity: "+ list[position].quantity+" lit"
        }else{
            holder.binding.proQuantity.text ="Quantity: "+ list[position].quantity
        }


        Glide.with(context).load(list[position].coverImg).into(holder.binding.productImg)






        when (list[position].status){

            "Cancel" ->{
                holder.binding.ProStatus.text = "Canceled"
                holder.binding.ProStatus.setTextColor(Color.parseColor("#FF0000"))
                holder.binding.textView24.isClickable = false
                holder.binding.textView24.setTextColor(Color.parseColor("#808080"))
            }

            "Ordered" -> {
                holder.binding.ProStatus.text = "Ordered"
                holder.binding.textView24.setOnClickListener {
                    updateStatus("Cancel",list[position].orderId.toString())
                    holder.binding.ProStatus.text = "Canceled"
                    holder.binding.ProStatus.setTextColor(Color.parseColor("#FF0000"))
                    holder.binding.textView24.setTextColor(Color.parseColor("#808080"))
                    Toast.makeText(context, "Your Order Canceled", Toast.LENGTH_SHORT).show()
                }
            }

            "Dispatched" ->{
                holder.binding.ProStatus.text = "Dispatched"
                holder.binding.textView24.isClickable = false
                holder.binding.textView24.setTextColor(Color.parseColor("#808080"))
            }

            "Delivered" -> {
                holder.binding.ProStatus.text = "Delivered"
                holder.binding.ProStatus.setTextColor(Color.parseColor("#008000"))
                holder.binding.textView24.isClickable = false
                holder.binding.textView24.setTextColor(Color.parseColor("#808080"))
            }
        }


    }



    fun updateStatus(str: String, doc:String){

        val data = hashMapOf<String, Any>()
        data["status"] =str
        Firebase.firestore.collection("allOrders")
            .document(doc).update(data)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
    }
}