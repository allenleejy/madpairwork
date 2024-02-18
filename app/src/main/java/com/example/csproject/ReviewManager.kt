package com.example.csproject

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ReviewManager(private val context: Context) {

    private val sharedPreferences = context.getSharedPreferences("ReviewPreferences", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun addReview(item: Review) {
        val items = getReviews().toMutableList()
        items.add(item)
        saveReviews(items)
    }

    fun removeReview(reviewName: String, reviewDescription: String) {
        val items = getReviews().toMutableList()
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            if (currentItem.name == reviewName && currentItem.description == reviewDescription) {
                iterator.remove()
                break
            }
        }
        saveReviews(items)
    }
    fun removeAllReviews() {
        saveReviews(emptyList())
    }

    fun getReviews(): List<Review> {
        val json = sharedPreferences.getString("reviewItems", null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<List<Review>>() {}.type)
        } else {
            emptyList()
        }
    }
    fun returnProductReviews(productName: String) : ArrayList<Review> {
        val items = getReviews().toMutableList()
        val products = ArrayList<Review>()
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            val currentItem = iterator.next()
            if (currentItem.productName == productName) {
                products.add(currentItem)
            }
        }
        return products
    }

    private fun saveReviews(items: List<Review>) {
        val json = gson.toJson(items)
        sharedPreferences.edit().putString("reviewItems", json).apply()
    }
}
