package com.example.ecommerceadroid.Models

class Product {
    private var pId = ""
    private var name = ""
    private var brand = ""
    private var qty = ""
    private var price = ""

    constructor()
    constructor(pId: String, name: String, brand: String, qty: String, price: String) {
        this.pId = pId
        this.name = name
        this.brand = brand
        this.qty = qty
        this.price = price
    }

    fun getPId ():String{
        return pId
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


    fun setPId(pId: String){
        this.pId = pId
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

}