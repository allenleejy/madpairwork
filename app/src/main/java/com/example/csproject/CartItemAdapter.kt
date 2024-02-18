import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.csproject.CartItem
import com.example.csproject.CartItemListener
import com.example.csproject.CartManager
import com.example.csproject.R

class CartItemAdapter(val CartList: List<CartItem>,val context: Context, val cartManager: CartManager, val listener: CartItemListener) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cart, parent, false)

        return ViewHolder(v, CartList, context, cartManager, listener)
    }

    override fun onBindViewHolder(holder: CartItemAdapter.ViewHolder, position: Int) {
        holder.bindItems(CartList[position])
    }

    override fun getItemCount() = CartList.size

    class ViewHolder(itemView: View, private val cartList: List<CartItem>, val context: Context, val cartManager: CartManager, val listener: CartItemListener) : RecyclerView.ViewHolder(itemView) {
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
                    listener.onPriceChange(cart, -1)
                    itemQuantityTextView.text = cart.itemQuantity.toString()
                    val cost = "$" + String.format("%.2f", cart.itemPrice * cart.itemQuantity)
                    itemPriceTextView.text = cost
                }
                else {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Confirm Removal")
                        .setMessage("Are you sure you want to remove this item from the cart?")
                        .setPositiveButton("Remove") { dialog, _ ->
                            //cartManager.removeItemFromCart(cart.itemName, cart.itemSize)
                            listener.onItemRemoved(cart)
                            listener.onPriceChange(cart, 0)
                            dialog.dismiss()
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            dialog.dismiss()
                        }.create().show()
                }
            }
            itemIncrease.setOnClickListener{
                cart.itemQuantity++
                itemQuantityTextView.text=cart.itemQuantity.toString()
                val cost = "$" + String.format("%.2f", cart.itemPrice * cart.itemQuantity)
                itemPriceTextView.text = cost
                listener.onPriceChange(cart, 1)
            }
        }
    }
}
