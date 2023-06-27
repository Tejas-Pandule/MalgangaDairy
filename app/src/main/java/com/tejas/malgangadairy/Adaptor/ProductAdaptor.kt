package com.tejas.malgangadairy.Adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tejas.malgangadairy.Activity.ProductDetailActivity

import com.tejas.malgangadairy.Model.Add_Product_Model
import com.tejas.malgangadairy.databinding.ProductItemLayoutBinding

class ProductAdaptor (val context : Context,val list : ArrayList<Add_Product_Model>) : RecyclerView.Adapter<ProductAdaptor.ViewHolder>() {

     inner class ViewHolder (val binding: ProductItemLayoutBinding) :RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdaptor.ViewHolder {
        val binding = ProductItemLayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductAdaptor.ViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView)
        holder.binding.textView6.text = data.productName
        holder.binding.textView8.text = data.productCategory
        holder.binding.textView9.text = "₹"+data.productMrp
        holder.binding.button3.text = "₹"+ data.productSp


        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return list.size
    }
}