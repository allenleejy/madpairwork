package com.example.csproject

import CartItemAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CartFragment : Fragment(), CartItemListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }
    private lateinit var cartAdapter: CartItemAdapter
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var costView : TextView
    private lateinit var checkout : Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartRecyclerView = requireView().findViewById(R.id.recycler_view_cart)
        val layoutManager = LinearLayoutManager(requireContext())
        cartRecyclerView.layoutManager = layoutManager
        val cartManager = CartManager(requireContext())
        costView = requireView().findViewById(R.id.total_cost)
        costView.text = cartManager.getTotalCost()
        cartAdapter = CartItemAdapter(cartManager.getCartItems(), requireContext(), cartManager, this)
        cartRecyclerView.adapter = cartAdapter
        checkout = requireView().findViewById(R.id.checkout_button)
        checkout.setOnClickListener {
            val price = cartManager.getTotalCost().substring(7)
            cartManager.removeAllItemsFromCart()
            cartRecyclerView.adapter = CartItemAdapter(cartManager.getCartItems(), requireContext(), cartManager, this)
            costView.text = "Total: $0.00"
            Toast.makeText(context, "Checkout Cart of " + price, Toast.LENGTH_LONG).show()
        }
    }
    override fun onItemRemoved(item: CartItem) {
        val cartManager = CartManager(requireContext())
        cartManager.removeItemFromCart(item.itemName, item.itemSize)
        cartAdapter = CartItemAdapter(cartManager.getCartItems(), requireContext(), cartManager, this)
        cartRecyclerView.adapter = cartAdapter
    }
    override fun onPriceChange(item: CartItem, change: Int) {
        val cartManager = CartManager(requireContext())
        if (change == 0) {
            costView.text = cartManager.getTotalCost()
        }
        else if (change == 1) {
            cartManager.addItemToCart(item)
            costView.text = cartManager.getTotalCost()
        }
        else {
            cartManager.removeItemFromCart(item.itemName, item.itemSize)
            cartManager.addItemToCart(item)
            costView.text = cartManager.getTotalCost()
        }
    }

}