package com.example.beansbay.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.beansbay.model.UserModel
import com.example.beansbay.model.UserPreference
import com.example.beansbay.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _spotProduct = MutableLiveData<List<Product>>()
    val spotProduct = _spotProduct

    private val _spotProductId = MutableLiveData<List<Results>>()
    val spotProductId = _spotProductId

    private val _simProduct = MutableLiveData<List<Product>>()
    val simProduct = _simProduct

    private val _simProductId = MutableLiveData<List<Results>>()
    val simProductId = _simProductId


    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun getSpotlightedProduct(){
        _isLoading.value = true
        val service = ApiConfig().getApiService().getRecommendedProduct()
        service.enqueue(object : Callback<ProductRecommendationResponse>{
            override fun onResponse(
                call: Call<ProductRecommendationResponse>,
                response: Response<ProductRecommendationResponse>
            ) {
                if(response.isSuccessful){
                    _spotProductId.value = response.body()!!.results
                    _spotProduct.value = response.body()!!.products
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ProductRecommendationResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }

        })
    }

    fun getRecommendedProduct(){
        _isLoading.value = true
        val service = ApiConfig().getApiService().getSimiliarProduct()
        service.enqueue(object: Callback <ProductSimiliarResponse>{
            override fun onResponse(
                call: Call<ProductSimiliarResponse>,
                response: Response<ProductSimiliarResponse>
            ) {
                if(response.isSuccessful) {
                    _simProductId.value = response.body()!!.similar
                    _simProduct.value = response.body()!!.products
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ProductSimiliarResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }

        })
    }


}