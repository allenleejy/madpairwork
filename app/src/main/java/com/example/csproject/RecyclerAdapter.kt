package com.example.csproject

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater

class RecyclerAdapter(val CardList: ArrayList<Card>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        return ViewHolder(v, CardList)
    }
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.bindItems(CardList[position])
    }
    override fun getItemCount() = CardList.size

    class ViewHolder(itemView: View, private val cardList: ArrayList<Card>) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetails: TextView

        init {
            itemImage = itemView.findViewById(R.id.cart_image)
            itemTitle = itemView.findViewById(R.id.review_name)
            itemDetails = itemView.findViewById(R.id.review_description)

            itemView.setOnClickListener{ view ->

                val pos = adapterPosition +1
                val context = view.context
                val intent = Intent(context, ItemDetailsActivity::class.java).apply {
                    putExtra("image", cardList[adapterPosition].images)
                    putExtra("title", cardList[adapterPosition].title)
                    putExtra("price", cardList[adapterPosition].detail)
                    putExtra("description", cardList[adapterPosition].description)
                }
                context.startActivity(intent)
            }
        }

        fun bindItems(chp : Card){
            itemTitle.text = chp.title
            itemDetails.text = chp.detail
            itemImage.setImageResource(chp.images)
        }
    }
}