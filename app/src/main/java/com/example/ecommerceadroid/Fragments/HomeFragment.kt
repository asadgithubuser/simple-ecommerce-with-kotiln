package com.example.ecommerceadroid.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceadroid.Adapters.ProductAdapter
import com.example.ecommerceadroid.Models.Product
import com.example.ecommerceadroid.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var mProductList:MutableList<Product>? = null
    private var pAdapter:ProductAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       var view = inflater.inflate(R.layout.fragment_home, container, false)


        var recyclerView = view.findViewById<RecyclerView>(R.id.productList_recyclerView)
        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = LinearLayoutManager(context)

        mProductList = ArrayList()
        pAdapter = context?.let { ProductAdapter(it, mProductList as ArrayList<Product>) }
        recyclerView.adapter = pAdapter

        getAllProducts()


        return view
    }

    private fun getAllProducts() {
        var productRef = FirebaseDatabase.getInstance().reference.child("Products")
        productRef.addValueEventListener( object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var product = item.getValue(Product::class.java)
                        mProductList!!.add(product!!)
                    }
                    pAdapter!!.notifyDataSetChanged()
                }
            }
        })

    }

}