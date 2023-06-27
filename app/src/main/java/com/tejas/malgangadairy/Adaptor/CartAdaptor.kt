package com.tejas.malgangadairy.Adaptor


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tejas.malgangadairy.Activity.ProductDetailActivity
import com.tejas.malgangadairy.databinding.LayoutCartItemBinding
import com.tejas.malgangadairy.roomdb.AppDatabase
import com.tejas.malgangadairy.roomdb.ProductModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdaptor (val context : Context, val list: List<ProductModel>): RecyclerView.Adapter<CartAdaptor.viewHolder>() {


    inner class viewHolder(val binding: LayoutCartItemBinding):RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context),parent,false)

        return viewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    @SuppressLint("SetTextI18n")
    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.imageView3)
        holder.binding.textView10.text = list[position].productName
        holder.binding.textView11.text ="₹ "+list[position].productSp

        // opening product detail Activity
        holder.itemView.setOnClickListener {
            val intent = Intent(context,ProductDetailActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }



        //delete item from cart Activity
        val dao = AppDatabase.getInstance(context).productDao()
        holder.binding.imageView4.setOnClickListener {

            //uses GlobalScope becoz of delete fun is suspended fun.
            GlobalScope.launch(Dispatchers.IO) {

                dao.deleteProduct(
                    ProductModel(
                    list[position].productId,
                    list[position].productName,
                    list[position].productSp,
                    list[position].productImage
                    )
                )
            }

        }
    }
}