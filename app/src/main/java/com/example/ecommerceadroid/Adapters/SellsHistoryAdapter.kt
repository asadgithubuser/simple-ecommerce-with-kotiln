package com.example.ecommerceadroid.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerceadroid.Models.Sell
import com.example.ecommerceadroid.R

class SellsHistoryAdapter (private val mContext: Context, private val mProductList: MutableList<Sell>)
    : RecyclerView.Adapter<SellsHistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SellsHistoryAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(R.layout.sell_history_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mProductList.size
    }

    override fun onBindViewHolder(holder: SellsHistoryAdapter.ViewHolder, position: Int) {
        var sell = mProductList[position]

        holder.name.text = sell.getName()
        holder.brand.text = sell.getBrand()
        holder.price.text = "price: "+sell.getPrice()+" tk"
        holder.qty.text = "Quantity: "+sell.getQty()+" pcs"
        holder.amount.text = "Total: "+sell.getSellAmount()+" tk"
        holder.date.text = "Sell Date: "+sell.getDate()
    }

    inner class ViewHolder(@NonNull itemView: View): RecyclerView.ViewHolder(itemView) {
        var name : TextView = itemView.findViewById(R.id.sell_name)
        var brand : TextView = itemView.findViewById(R.id.sell_brand)
        var price : TextView = itemView.findViewById(R.id.sell_price)
        var qty : TextView = itemView.findViewById(R.id.sell_qty)
        var amount : TextView = itemView.findViewById(R.id.sellAmount)
        var date : TextView = itemView.findViewById(R.id.sellDate)
    }

}