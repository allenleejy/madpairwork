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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = requireView().findViewById<RecyclerView>(R.id.recycler_view)

        val layoutManager = GridLayoutManager(requireActivity(),2)

        recyclerView.layoutManager = layoutManager

        CardList.add(Card("Red Long sleeved T-shirt", "$99.99", R.drawable.shirt1))
        CardList.add(Card("Blue long sleeved T-shirt", "$100.04", R.drawable.shirt2))
        CardList.add(Card("Black pants", "$50.50", R.drawable.pants1))
        CardList.add(Card("Green pants", "$50.50", R.drawable.pants2))
        CardList.add(Card("Green flower formal", "$100.00", R.drawable.formal1))
        CardList.add(Card("Blue flower formal", "$110.00", R.drawable.formal2))
        CardList.add(Card("Garden socks", "$20.00", R.drawable.socks))
        CardList.add(Card("Cobra belt", "$50.00", R.drawable.belt))
        val adapter = RecyclerAdapter(CardList)
        recyclerView.adapter = adapter

    }


}