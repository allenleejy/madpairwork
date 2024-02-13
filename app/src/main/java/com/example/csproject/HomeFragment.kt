package com.example.csproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {
    private val CardList = ArrayList<Card>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)

        val layoutManager = GridLayoutManager(requireActivity(),2)

        recyclerView.layoutManager = layoutManager

        CardList.add(Card("Red T-shirt", "$99.99", R.drawable.shirt1))
        CardList.add(Card("Blue T-shirt", "$99.99", R.drawable.shirt2))
        CardList.add(Card("Black Pants", "$49.99", R.drawable.pants1))
        CardList.add(Card("Green Pants", "$49.99", R.drawable.pants2))
        CardList.add(Card("Green Formal", "$109.99", R.drawable.formal1))
        CardList.add(Card("Blue Formal", "$109.99", R.drawable.formal2))
        CardList.add(Card("Garden Socks", "$19.99", R.drawable.socks))
        CardList.add(Card("Cobra Belt", "$49.99", R.drawable.belt))
        val adapter = RecyclerAdapter(CardList)
        recyclerView.adapter = adapter

    }


}