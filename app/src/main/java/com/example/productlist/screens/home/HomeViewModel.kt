package com.example.productlist.screens.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.productlist.models.DummyProduct
import com.example.productlist.models.Product
import com.example.productlist.retrofit.IProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var productService: IProductService? = null
    val isFailed = MutableLiveData<Boolean>()
    val products = MutableLiveData<List<Product>>()

    fun getProducts() {
        productService?.products()?.enqueue(object : Callback<DummyProduct?> {
            override fun onResponse(call: Call<DummyProduct?>, response: Response<DummyProduct?>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        products.postValue(responseBody.products)
                        isFailed.postValue(false)
                        Log.d("HomeViewModel", "Products loaded successfully")
                    } else {
                        isFailed.postValue(true)
                        Log.d("HomeViewModel", "Response body is null")
                    }
                } else {
                    isFailed.postValue(true)
                    Log.d("HomeViewModel", "Response unsuccessful: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<DummyProduct?>, t: Throwable) {
                isFailed.postValue(true)
                Log.d("HomeViewModel", "Network call failed: ${t.message}")
            }
        })
    }
}
