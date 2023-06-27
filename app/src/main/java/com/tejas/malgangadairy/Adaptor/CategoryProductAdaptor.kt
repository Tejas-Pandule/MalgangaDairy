package com.tejas.malgangadairy.Adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tejas.malgangadairy.Activity.ProductDetailActivity
import com.tejas.malgangadairy.Model.Add_Product_Model
import com.tejas.malgangadairy.databinding.ItemCategoryProductLayoutBinding

class CategoryProductAdaptor (val context:Context, val list: ArrayList<Add_Product_Model>)
    : RecyclerView.Adapter<CategoryProductAdaptor.ViewHolder>()
{

    inner class ViewHolder (val binding:ItemCategoryProductLayoutBinding):RecyclerView.ViewHolder(binding.root){


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  binding = ItemCategoryProductLayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageView2)

        holder.binding.textView.text = list[position].productName
        holder.binding.textView2.text = "₹"+list[position].productSp


        holder.itemView.setOnClickListener {
            val intent = Intent(context,ProductDetailActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }

    }
}