package com.example.ecommerceadroid.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceadroid.Adapters.ProductAdapter
import com.example.ecommerceadroid.Adapters.SellsHistoryAdapter
import com.example.ecommerceadroid.Models.Sell
import com.example.ecommerceadroid.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SellHistoryFragment : Fragment() {

    private var mProductList:MutableList<Sell>? = null
    private var pAdapter: SellsHistoryAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_sell_history, container, false)


        var recyclerView = view.findViewById<RecyclerView>(R.id.sellHistory_recyclerview)
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(context)

        mProductList = ArrayList()
        pAdapter = context?.let { SellsHistoryAdapter(it, mProductList as ArrayList<Sell>) }
        recyclerView.adapter = pAdapter

        getAllProducts()


        return view
    }

    private fun getAllProducts() {
        var productRef = FirebaseDatabase.getInstance().reference.child("Sells")
        productRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var product = item.getValue(Sell::class.java)
                        mProductList!!.add(product!!)
                    }
                    pAdapter!!.notifyDataSetChanged()
                }
            }
        })

    }

}