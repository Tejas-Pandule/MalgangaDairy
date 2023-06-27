package com.tejas.malgangadairy.Adaptor

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tejas.malgangadairy.Activity.CategoryActivity
import com.tejas.malgangadairy.Model.Modelclass
import com.tejas.malgangadairy.R

class CategoryAdaptor (val context: Context, var List:ArrayList<Modelclass>) : RecyclerView.Adapter<CategoryAdaptor.ViewHolder>() {

    class ViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){

        val img = itemView.findViewById<ImageView>(R.id.imgView)
        val txt = itemView.findViewById<TextView>(R.id.txtView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.category_item_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return List.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

       holder.txt.text =List[position].cat
       Glide.with(context).load(List[position].img).into(holder.img)

        holder.itemView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, CategoryActivity::class.java)
            intent.putExtra("cat",List[position].cat)
            context.startActivity(intent)
        })


    }
}