package com.example.ecommerceadroid.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceadroid.Models.Product
import com.example.ecommerceadroid.R

class ProductAdapter(private val mContext: Context, private val mProductList: MutableList<Product>)
    :RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mProductList.size
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        var product = mProductList[position]

        holder.name.text = product.getName()
        holder.brand.text = product.getBrand()
        holder.price.text = "price: "+product.getPrice()+" tk"
        holder.qty.text = "Quantity: "+product.getQty()+" pcs"
    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.name)
        var brand : TextView = itemView.findViewById(R.id.brand)
        var price : TextView = itemView.findViewById(R.id.price)
        var qty : TextView = itemView.findViewById(R.id.qty)
    }

}