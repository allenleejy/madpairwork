package com.example.csproject

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.material.snackbar.Snackbar

class ItemDetailsActivity : AppCompatActivity() {
    private lateinit var selectedSize : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main2)
        val cartManager = CartManager(this)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var imageView = findViewById<ImageView>(R.id.cart_image)
        val titleTextView = findViewById<TextView>(R.id.item_title)
        val priceTextView = findViewById<TextView>(R.id.item_price)
        val descriptionTextView = findViewById<TextView>(R.id.text_details)

        val spinner = findViewById<Spinner>(R.id.spinner_quantity)

        val radio = findViewById<RadioGroup>(R.id.radio_group_size)
        radio.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            selectedSize = selectedRadioButton.text.toString()
        }
        ArrayAdapter.createFromResource(this, R.array.quantity_options, android.R.layout.simple_spinner_item).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }

        val imageResource = intent.getIntExtra("image", R.drawable.boutiquelogo)
        val title = intent.getStringExtra("title") ?: "Title Not Found"
        val price = intent.getStringExtra("price") ?: "Price Not Found"
        val description = intent.getStringExtra("description") ?: "Description not Found"
        imageView.setImageResource(imageResource)
        titleTextView.text = title
        priceTextView.text = price
        descriptionTextView.text = description

        val addCart = findViewById<Button>(R.id.btn_add_to_cart)
        addCart.setOnClickListener {
            val priced = price.substring(1).toDouble()
            val newItem = CartItem(imageResource, title, selectedSize, spinner.selectedItem.toString().toInt(), priced)
            cartManager.addItemToCart(newItem)
            Snackbar.make(it, "Item has been added to cart!", Snackbar.LENGTH_LONG).show()
        }
    }
}
