package com.example.csproject

import CartItemAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class CartFragment : Fragment() {

    private val CartList = ArrayList<CartItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cartRecyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view_cart)
        val layoutManager = LinearLayoutManager(requireContext())
        cartRecyclerView.layoutManager = layoutManager

        CartList.add(CartItem(R.drawable.formal1,"Green Formal", "M", 3, 49.99))
        CartList.add(CartItem(R.drawable.formal2,"Blue Formal", "M", 2, 49.99))
        CartList.add(CartItem(R.drawable.belt,"Cobra Belt", "S", 1, 49.99))
        val cartAdapter = CartItemAdapter(CartList)
        cartRecyclerView.adapter = cartAdapter
    }

}