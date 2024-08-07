package com.example.productlist.screens.productdetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productlist.models.Product
import com.example.productlist.retrofit.IProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailViewModel : ViewModel() {

    var product = MutableLiveData<Product?>()
    var productService : IProductService? = null
    var isFailed = MutableLiveData<Boolean>()

    fun getProduct(id : Int){
        productService?.getProductById(id)?.enqueue(object : Callback<Product?>{
            override fun onResponse(call: Call<Product?>, response: Response<Product?>) {
                if(response.isSuccessful){
                    val response = response.body()
                    if(response != null){
                        product.value = response
                        isFailed.value = false
                    }
                }else{
                    isFailed.value = true
                }
            }

            override fun onFailure(call: Call<Product?>, t: Throwable) {
                isFailed.value = true
            }

        })
    }
}