package com.example.csproject

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CartManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("CartPreferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addItemToCart(item: CartItem) {
        val items = getCartItems().toMutableList()
        val iterator = items.iterator()
        var added = false
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            if (currentItem.itemName == item.itemName && currentItem.itemSize == item.itemSize) {
                currentItem.itemQuantity++
                removeItemFromCart(item.itemName, item.itemSize)
                addItemToCart(currentItem)
                added = true
                break
            }
        }
        if (!added) {
            items.add(item)
            saveCartItems(items)
        }
    }

    fun removeItemFromCart(itemName: String, itemSize: String) {
        val items = getCartItems().toMutableList()
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            if (currentItem.itemName == itemName && currentItem.itemSize == itemSize) {
                iterator.remove()
                break
            }
        }
        saveCartItems(items)
    }
    fun removeAllItemsFromCart() {
        saveCartItems(emptyList())
    }

    fun getTotalCost(): String {
        val items = getCartItems().toMutableList()
        val iterator = items.iterator()
        var totalcost = 0.0
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            totalcost += currentItem.itemPrice * currentItem.itemQuantity.toDouble()
        }
        return "Total: $" + String.format("%.2f", totalcost)
    }

    fun getCartItems(): List<CartItem> {
        val json = sharedPreferences.getString("cartItems", null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<CartItem>>() {}.type)
        } else {
            emptyList()
        }
    }

    private fun saveCartItems(items: List<CartItem>) {
        val json = gson.toJson(items)
        sharedPreferences.edit().putString("cartItems", json).apply()
    }
}
