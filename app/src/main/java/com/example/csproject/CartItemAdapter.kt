import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.csproject.CartItem
import com.example.csproject.R
import org.w3c.dom.Text

class CartItemAdapter(val CartList: List<CartItem>) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)
        return ViewHolder(v, CartList)
    }

    override fun onBindViewHolder(holder: CartItemAdapter.ViewHolder, position: Int) {
        holder.bindItems(CartList[position])
    }

    override fun getItemCount() = CartList.size

    class ViewHolder(itemView: View, private val cartList: List<CartItem>) : RecyclerView.ViewHolder(itemView) {
        var itemImageView: ImageView
        var itemNameTextView: TextView
        var itemSizeTextView: TextView
        var itemQuantityTextView: TextView
        var itemPriceTextView: TextView
        var itemDecrease: Button
        var itemIncrease: Button

        init {
            itemImageView = itemView.findViewById(R.id.cart_image)
            itemNameTextView = itemView.findViewById(R.id.cart_name)
            itemSizeTextView = itemView.findViewById(R.id.cart_size)
            itemQuantityTextView = itemView.findViewById(R.id.quantity_text)
            itemDecrease = itemView.findViewById(R.id.btn_decrease)
            itemIncrease = itemView.findViewById(R.id.btn_increase)
            itemPriceTextView = itemView.findViewById(R.id.cart_price)



        }
        @SuppressLint("SetTextI18n")
        fun bindItems(cart : CartItem){
            itemImageView.setImageResource(cart.itemImageResId)
            itemNameTextView.text = cart.itemName
            itemSizeTextView.text = "Size: "+ cart.itemSize
            itemQuantityTextView.text = cart.itemQuantity.toString()
            val cost = "$" + String.format("%.2f", cart.itemPrice * cart.itemQuantity)
            itemPriceTextView.text = cost
            itemDecrease.setOnClickListener{
                if(cart.itemQuantity > 1) {
                    cart.itemQuantity--
                    itemQuantityTextView.text = cart.itemQuantity.toString()
                    val cost = "$" + String.format("%.2f", cart.itemPrice * cart.itemQuantity)
                    itemPriceTextView.text = cost
                }
            }
            itemIncrease.setOnClickListener{
                cart.itemQuantity++
                itemQuantityTextView.text=cart.itemQuantity.toString()
                val cost = "$" + String.format("%.2f", cart.itemPrice * cart.itemQuantity)
                itemPriceTextView.text = cost
            }
        }
    }
}
