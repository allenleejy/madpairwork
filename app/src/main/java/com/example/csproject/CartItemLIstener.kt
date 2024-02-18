package com.example.csproject

interface CartItemListener {
    fun onItemRemoved(item: CartItem)
    fun onPriceChange(item: CartItem, change: Int)
}
