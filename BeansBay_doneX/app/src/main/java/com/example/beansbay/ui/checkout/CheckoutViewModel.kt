package com.example.beansbay.ui.checkout

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beansbay.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CheckoutViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading = _isLoading

    private val _listCheckout = MutableLiveData<Cart>()
    var listCheckout = _listCheckout

    private val _statusDelete = MutableLiveData<Boolean>()
    var statusDelete = _statusDelete

    private val _checkOutStatus = MutableLiveData<Boolean>()
    var checkOutStatus = _checkOutStatus

    private val _checkOutData = MutableLiveData<CheckoutCartResponse>()
    var checkOutData = _checkOutData

    fun getCartItems(){
        _isLoading.value = true
        val service = ApiConfig().getApiService().getCart()
        service.enqueue(object: Callback<Cart>{
            override fun onResponse(call: Call<Cart>, response: Response<Cart>) {
                if(response.isSuccessful){
                    _listCheckout.value = response.body()
                    _isLoading.value = false
                }else{
                    _isLoading.value = false
                }
            }
            override fun onFailure(call: Call<Cart>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }
        })
    }

    fun deleteProduct(id : String){
        _isLoading.value = true
        val service = ApiConfig().getApiService().deleteProduct(id)
        service.enqueue(object : Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    _statusDelete.value = true
                }else{
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }

        })
    }

    fun checkOut(){
        _isLoading.value = true
        val service = ApiConfig().getApiService().checkoutCart()
        service.enqueue(object : Callback<CheckoutCartResponse>{
            override fun onResponse(call: Call<CheckoutCartResponse>, response: Response<CheckoutCartResponse>) {
                if(response.isSuccessful){
                    _isLoading.value = false
                    _checkOutData.value = response.body()
                    _checkOutStatus.value = true
                }
            }

            override fun onFailure(call: Call<CheckoutCartResponse>, t: Throwable) {
                _isLoading.value = false
                _checkOutStatus.value = false
            }

        })
    }
}