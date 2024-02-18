package com.example.csproject

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    private lateinit var drawerLayout:DrawerLayout
    private lateinit var reviewManager: ReviewManager
    private lateinit var memberDatabase: MemberDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val products = resources.getStringArray(R.array.product_options)
        val buttonPressed = intent.getBooleanExtra("goreview", false)
        val reviewname = intent.getStringExtra("product")

        if (buttonPressed) {
            val ratingFragment = RatingFragment.newInstance( products.indexOf(reviewname)?: 0)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,ratingFragment).commit()
            val menuItem = navigationView.menu.findItem(R.id.nav_rate)
            menuItem?.isChecked = true

        }
        else {

            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, HomeFragment()).commit()
        }

        drawerLayout = findViewById(R.id.drawer_layout)

        reviewManager = ReviewManager(this)

        memberDatabase = MemberDatabase(this)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open_nav,R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val fab = findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,CartFragment()).commit()
            val menuItem = navigationView.menu.findItem(R.id.nav_cart)
            menuItem?.isChecked = true
        }


    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_cart -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,CartFragment()).commit()
        }
        when(item.itemId){
            R.id.nav_member -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,MemberFragment()).commit()
        }
        when(item.itemId){
            R.id.nav_rate -> supportFragmentManager.beginTransaction().replace(R.id.fragment_container,RatingFragment()).commit()
        }
        when(item.itemId){
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.fragment_container,HomeFragment()).commit()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed(){
        super.onBackPressed()

        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            onBackPressedDispatcher.onBackPressed()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.info -> {
                initReviews()
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Contact Information")
                alertDialogBuilder.setMessage("Email: support@bboutique.com\n\nPhone: 88888888")
                alertDialogBuilder.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
                    dialog.dismiss()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                true
            }
            R.id.about -> {
                memberDatabase.removeAllMembers()
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("About Us")
                alertDialogBuilder.setMessage("Blossom Boutique is an emerging fashion retailer around the world!")
                alertDialogBuilder.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
                    dialog.dismiss()
                }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    fun onTouch(view: View) {
        closeKeyBoard()
    }
    fun initReviews() {
        val reviewManager = ReviewManager(this)
        reviewManager.removeAllReviews()

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
