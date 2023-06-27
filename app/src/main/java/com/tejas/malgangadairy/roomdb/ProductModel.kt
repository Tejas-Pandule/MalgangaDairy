package com.tejas.malgangadairy.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductModel(
    @PrimaryKey
    val productId :String,

    @ColumnInfo(name = "productName")
    val productName: String? = "",

    @ColumnInfo(name = "productSp")
    val productSp : String? = "",

    @ColumnInfo(name = "productImage")
    val productImage: String? = "",

    @ColumnInfo(name = "Quantity")
    val productQuantity:String?="",



)
