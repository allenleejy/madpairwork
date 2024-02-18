package com.example.csproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ArrayAdapter
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
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
        val titleTextView = findViewById<TextView>(R.id.review_name)
        val priceTextView = findViewById<TextView>(R.id.item_price)
        val descriptionTextView = findViewById<TextView>(R.id.text_details)

        val spinner = findViewById<Spinner>(R.id.spinner_quantity)
        selectedSize = "none"

        val radio = findViewById<RadioGroup>(R.id.radio_group_size)
        radio.setOnCheckedChangeListener { group, checkedId ->
            val selectedRadioButton = findViewById<RadioButton>(checkedId)
            selectedSize = selectedRadioButton.text.toString()
        }
        ArrayAdapter.createFromResource(this, R.array.quantity_options, android.R.layout.simple_spinner_item).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }

        val addReview = findViewById<Button>(R.id.add_review_btn)
        addReview.setOnClickListener {

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
            if (selectedSize != "none") {
                val priced = price.substring(1).toDouble()
                val newItem = CartItem(
                    imageResource,
                    title,
                    selectedSize,
                    spinner.selectedItem.toString().toInt(),
                    priced
                )
                cartManager.addItemToCart(newItem)
                Snackbar.make(it, "Item has been added to cart!", Snackbar.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(this, "Select a Size Option", Toast.LENGTH_SHORT).show()
            }
        }

        val displayRating = findViewById<TextView>(R.id.review_upper)
        val displayAmount = findViewById<TextView>(R.id.review_amount_upper)

        val reviewManager = ReviewManager(this)
        initReviews()
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_reviews)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        var averageRating = 0.0
        val adapter = ReviewAdapter(reviewManager.returnProductReviews(title))
        for (review in reviewManager.returnProductReviews(title)) {
            averageRating += review.rating
        }
        averageRating /= reviewManager.returnProductReviews(title).size
        var text_review = findViewById<TextView>(R.id.text_rating)
        text_review.text = String.format("%.1f", averageRating)
        var review_bar = findViewById<RatingBar>(R.id.rating_bar)
        displayRating.text = String.format("%.2f", averageRating)
        displayAmount.text = "(" + reviewManager.returnProductReviews(title).size.toString() + ")"

        review_bar.rating = averageRating.toFloat()
        recyclerView.adapter = adapter
    }
    fun initReviews() {
        val reviewManager = ReviewManager(this)

        reviewManager.addReview(Review("Susan", "Amazing T-shirt", 4.5, "Red T-shirt"))
        reviewManager.addReview(Review("John", "Great T-shirt", 4.0, "Red T-shirt"))
        reviewManager.addReview(Review("Emily", "Nice T-shirt", 4.0, "Blue T-shirt"))

        reviewManager.addReview(Review("Michael", "Excellent Pants", 4.5, "Black Pants"))
        reviewManager.addReview(Review("Sophia", "Comfortable Pants", 4.0, "Green Pants"))
        reviewManager.addReview(Review("Daniel", "Good Pants", 3.5, "Green Pants"))

        reviewManager.addReview(Review("Olivia", "Great Formal Shirt", 4.5, "Green Formal"))
        reviewManager.addReview(Review("Matthew", "Presentable Blue Shirt", 4.0, "Blue Formal"))
        reviewManager.addReview(Review("Amanda", "Comfortable Sock", 3.5, "Garden Sock"))
        reviewManager.addReview(Review("Ava", "Disappointing Belt", 2.0, "Cobra Belt"))
    }
}
