package com.example.csproject

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.view.ViewGroup
import android.widget.ImageView
import android.view.LayoutInflater
import com.google.android.material.snackbar.Snackbar

class RecyclerAdapter(val CardList: ArrayList<Card>) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)

    }
    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.bindItems(CardList[position])
    }
    override fun getItemCount() = CardList.size

    // The class holding the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView
        var itemTitle: TextView
        var itemDetails: TextView

        init {
            itemImage = itemView.findViewById(R.id.item_image)
            itemTitle = itemView.findViewById(R.id.item_title)
            itemDetails = itemView.findViewById(R.id.item_detail)

            itemView.setOnClickListener{ view ->
                val pos = adapterPosition +1
                Snackbar.make(view, "Click detected on item $pos", Snackbar.LENGTH_LONG)
                    .setAction("Action",null).show()
            }
        }

        fun bindItems(chp : Card){
            itemTitle.text = chp.title
            itemDetails.text = chp.detail
            itemImage.setImageResource(chp.images)
        }
    }
}