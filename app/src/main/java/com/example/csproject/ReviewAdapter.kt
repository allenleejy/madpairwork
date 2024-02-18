package com.example.csproject

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.RatingBar

class ReviewAdapter(val reviewList: ArrayList<Review>) : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_layout, parent, false)
        return ViewHolder(v, reviewList)
    }
    override fun onBindViewHolder(holder: ReviewAdapter.ViewHolder, position: Int) {
        holder.bindItems(reviewList[position])
    }
    override fun getItemCount() = reviewList.size

    class ViewHolder(itemView: View, private val reviewList: ArrayList<Review>) : RecyclerView.ViewHolder(itemView) {
        var reviewName: TextView
        var reviewDescription: TextView
        var reviewRating: RatingBar

        init {
            reviewName = itemView.findViewById(R.id.review_name)
            reviewDescription = itemView.findViewById(R.id.review_description)
            reviewRating = itemView.findViewById(R.id.review_rating)

        }

        fun bindItems(rev : Review){
            reviewName.text = rev.name
            reviewDescription.text = rev.description
            reviewRating.rating = rev.rating.toFloat()
        }
    }
}