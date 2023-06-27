package com.tejas.malgangadairy.Model

data class Add_Product_Model(

    val productName: String? = "",
    val productDescription: String? = "",
    val productCoverImg: String? = "",
    val productCategory: String? = "",
    val productId: String? = "",
    val productMrp:String? = "",
    val productSp : String = "",
    val productImages: ArrayList<String> = ArrayList()
)
