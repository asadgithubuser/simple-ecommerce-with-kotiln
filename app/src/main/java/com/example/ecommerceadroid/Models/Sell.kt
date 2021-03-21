package com.example.ecommerceadroid.Models

class Sell {
    private var sId = ""
    private var name = ""
    private var brand = ""
    private var qty = ""
    private var price = ""
    private var sellAmount = ""
    private var date = ""

    constructor()
    constructor(sId: String, name: String, brand: String, qty: String, price: String, sellAmount: String, date: String ) {
        this.sId = sId
        this.name = name
        this.brand = brand
        this.qty = qty
        this.price = price
        this.sellAmount = sellAmount
        this.date = date
    }

    fun getPId ():String{
        return sId
    }

    fun getName ():String{
        return name
    }

    fun getBrand ():String{
        return brand
    }

    fun getQty ():String{
        return qty
    }

    fun getPrice():String{
        return price
    }

    fun getSellAmount():String{
        return sellAmount
    }
    fun setPId(sId: String){
        this.sId = sId
    }

    fun setName(name: String){
        this.name = name
    }

    fun setBrand(brand: String){
        this.brand = brand
    }

    fun setQty(qty: String){
        this.qty = qty
    }

    fun setPrice(price: String){
        this.price = price
    }

    fun setSellAmount(sellAmount: String){
        this.sellAmount = sellAmount
    }


    fun setDate(date: String){
        this.date = date
    }
    fun getDate():String{
        return date
    }


}