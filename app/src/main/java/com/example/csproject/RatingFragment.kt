package com.example.csproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.Spinner
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class RatingFragment : Fragment() {

    private lateinit var reviewName: TextInputEditText
    private lateinit var reviewDescription: TextInputEditText
    private lateinit var reviewRatingBar: RatingBar
    private var productName: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        productName = arguments?.getInt(ARG_PRODUCT_NAME) ?: 0
        return inflater.inflate(R.layout.fragment_rating, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reviewManager = ReviewManager(requireContext())
        val spinner = view.findViewById<Spinner>(R.id.spinner_product)
        reviewRatingBar = view.findViewById(R.id.reviewratingbar)
        ArrayAdapter.createFromResource(requireContext(), R.array.product_options, android.R.layout.simple_spinner_item).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            spinner.adapter = adapter
        }
        spinner.setSelection(productName)
        reviewName = view.findViewById(R.id.edit_text_name)
        reviewDescription = view.findViewById(R.id.edit_text_description)

        val reviewButton = view.findViewById<Button>(R.id.btn_add_review)
        reviewButton.setOnClickListener {
            if (reviewName.text.toString().isBlank() || reviewDescription.text.toString().isBlank() || reviewRatingBar.rating == 0.0.toFloat()) {
                Log.d("it got here", "hi")
                if (reviewName.text.toString() == "") {
                    reviewName.error = "Enter a Name"
                }
                if (reviewDescription.text.toString() == "") {
                    reviewDescription.error = "Add a Description"
                }
                if (reviewRatingBar.rating == 0.0.toFloat()) {
                    Toast.makeText(requireContext(), "Give a Rating", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                reviewManager.addReview(Review(reviewName.text.toString(), reviewDescription.text.toString(), reviewRatingBar.rating.toDouble(), spinner.selectedItem.toString()))
                Snackbar.make(view, "Review for " + spinner.selectedItem.toString() + " has been added", Snackbar.LENGTH_LONG).show()
                reviewName.text?.clear()
                reviewDescription.text?.clear()
                reviewRatingBar.rating = 0.0.toFloat()
            }
        }

    }
    companion object {
        private const val ARG_PRODUCT_NAME = "product_name"

        fun newInstance(productName: Int): RatingFragment {
            val fragment = RatingFragment()
            val args = Bundle().apply {
                putInt(ARG_PRODUCT_NAME, productName)
            }
            fragment.arguments = args
            return fragment
        }
    }

}