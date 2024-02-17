package com.example.csproject

data class CartItem(
    val itemImageResId: Int,
    val itemName: String,
    val itemSize: String,
    var itemQuantity: Int,
    val itemPrice: Double
)
