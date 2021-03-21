package com.example.ecommerceadroid.Fragments

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.example.ecommerceadroid.MainActivity
import com.example.ecommerceadroid.Models.Product
import com.example.ecommerceadroid.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_sell_product.*
import kotlinx.android.synthetic.main.fragment_sell_product.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.system.exitProcess


class SellProductFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var pNames: ArrayList<String>? = null

     var selected_pname: String = ""
     var sell_brand_name: String = ""
     var todayDate:  String = ""
     var productQty:  String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_sell_product, container, false)



        var name_field = view.findViewById<AutoCompleteTextView>(R.id.sell_product_nmList)
        pNames = ArrayList()
        var adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_checked, pNames!!)
        name_field.threshold = 1
        name_field.setAdapter(adapter)
        name_field.onItemClickListener = AdapterView.OnItemClickListener{ parent, view, position, id ->
            selected_pname = parent.getItemAtPosition(position).toString()

            selectBrandName(selected_pname)

        }

        var sdf = SimpleDateFormat("dd/M/yyyy")
        todayDate = sdf.format(Date())

        view.sell_p_date.setText(todayDate)

        getpNaemes()

        view.sell_product_btn.setOnClickListener {
            sellProduct()
        }


         return view
    }


    private fun showToast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show()
    }

    private fun sellProduct() {
        var pName = sell_product_nmList.text.toString()
        var pBrandName = sell_brand_name22.text.toString()
        var qty = sell_product_qty.text.toString()
        var price = sell_product_price.text.toString()

        when{
            TextUtils.isEmpty(pName) -> showToast("product name is required")
            TextUtils.isEmpty(pBrandName) -> showToast("barand name is required")
            TextUtils.isEmpty(qty) -> showToast("product qty is required")
            TextUtils.isEmpty(price) -> showToast("product price is required")

            else -> {
                if(productQty.toInt() >= qty.toInt()){
                    updateProductQty(pName, qty)

                    var productRef = FirebaseDatabase.getInstance().reference.child("Sells")
                    var sId = productRef.push().key
                    var sellamount: Int = qty.toInt() * price.toInt()
                    var sellProductMap = HashMap<String, Any>()

                    sellProductMap["sId"] = sId!!
                    sellProductMap["name"] = pName
                    sellProductMap["brand"] = pBrandName
                    sellProductMap["qty"] = qty
                    sellProductMap["price"] = price
                    sellProductMap["sellAmount"] = sellamount.toString()
                    sellProductMap["date"] = todayDate

                    productRef.child(sId).setValue(sellProductMap).addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            showToast("Product sold successfully")
                        }else{
                            showToast("Error: "+task.exception.toString())
                        }
                    }
                }else{
                    showToast("product qty is suffcient")
                }
            }
        }
    }

    private fun updateProductQty(pName: String, qty: String) {

        var ref = FirebaseDatabase.getInstance().reference.child("Products")
        ref.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var product = item.getValue(Product::class.java)
                        if(product!!.getName() == pName){
                            updateQtyToFirebase(product.getPId(), product.getQty(), qty)
                        }
                    }
                }
            }
        })
    }

    private fun updateQtyToFirebase(pid:String, pQty: String, currentQty: String) {
        var ref = FirebaseDatabase.getInstance().reference.child("Products")
        var pMap = HashMap<String, Any>()
        var qtyy = pQty.toInt()
        qtyy -= currentQty.toInt()
        pMap["qty"] = qtyy.toString()
        ref.child(pid).updateChildren(pMap)
    }

    private fun selectBrandName(selectedPname: String) {
        var ref = FirebaseDatabase.getInstance().reference.child("Products")
        ref.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var product = item.getValue(Product::class.java)
                        if(product!!.getName() == selectedPname){
                            sell_brand_name22.setText(product!!.getBrand())
                            productQty = product.getQty()
                        }
                    }
                }
            }
        })
    }

    private fun getpNaemes() {
        var ref = FirebaseDatabase.getInstance().reference.child("Products")
        ref.addValueEventListener( object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for(item in snapshot.children){
                        var product = item.getValue(Product::class.java)
                        pNames!!.add(product!!.getName())
                    }
                }
            }
        })
    }

}