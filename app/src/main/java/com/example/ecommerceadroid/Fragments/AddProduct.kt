package com.example.ecommerceadroid.Fragments

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.example.ecommerceadroid.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_add_product.*
import kotlinx.android.synthetic.main.fragment_add_product.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


/**
 * A simple [Fragment] subclass.
 * Use the [AddProduct.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProduct : Fragment() {

    var currentDate: String? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_add_product, container, false)

        val sdf = SimpleDateFormat("dd/M/yyyy")
        currentDate = sdf.format(Date())

        view.p_add_date.setText(currentDate)

        view.add_product.setOnClickListener {
            addNewProduct()
        }

        return view
    }

    private fun showToast(s: String) {
        Toast.makeText(context, s, Toast.LENGTH_LONG).show()
    }

    private fun addNewProduct() {

        var pName = product_name.text.toString()
        var pBrandName = brand_name.text.toString()
        var qty = product_qty.text.toString()
        var price = product_price.text.toString()


        when{
            TextUtils.isEmpty(pName) -> showToast("product name is required")
            TextUtils.isEmpty(pBrandName) -> showToast("barand name is required")
            TextUtils.isEmpty(qty) -> showToast("product qty is required")
            TextUtils.isEmpty(price) -> showToast("product price is required")

            else -> {

                var productRef = FirebaseDatabase.getInstance().reference.child("Products")
                var pId = productRef.push().key
                var ProductMap = HashMap<String, Any>()

                ProductMap["pId"] = pId!!
                ProductMap["name"] = pName
                ProductMap["brand"] = pBrandName
                ProductMap["qty"] = qty.toInt()
                ProductMap["price"] = price
                ProductMap["date"] = currentDate!!

                productRef.child(pId).setValue(ProductMap).addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        showToast("Product added successfully")
                    }else{
                        showToast("Error: "+task.exception.toString())
                    }
                }
            }
        }

    }

}