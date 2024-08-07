package com.example.productlist.retrofit

import com.example.productlist.models.DummyProduct
import com.example.productlist.models.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface IProductService {
    @GET("products")
    fun products(): Call<DummyProduct?>

    @GET("products/{id}")
    fun getProductById(@Path("id") id: Int): Call<Product?>
}