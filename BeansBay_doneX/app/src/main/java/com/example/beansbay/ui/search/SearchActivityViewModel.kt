package com.example.beansbay.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beansbay.network.ApiConfig
import com.example.beansbay.network.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivityViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _searchResult = MutableLiveData<List<Product>>()
    val searchResult = _searchResult

    fun searchProduct(query : String){
        _isLoading.value = true
        val service = ApiConfig().getApiService().searchProduct(query)
        service.enqueue(object: Callback<List<Product>> {
            override fun onResponse(
                call: Call<List<Product>>,
                response: Response<List<Product>>
            ) {
                _isLoading.value = false
                _searchResult.value = response.body()
            }

            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }

        })
    }
}