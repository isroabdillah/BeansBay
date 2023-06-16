package com.example.beansbay.ui.history

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beansbay.network.ApiConfig
import com.example.beansbay.network.TransaksiResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryActivityViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading = _isLoading

    private val _transaksiData = MutableLiveData<List<TransaksiResponse>>()
    var transaksiData = _transaksiData

    fun getTransaksiHistory(){
        _isLoading.value = true
        val service = ApiConfig().getApiService().getTransaksiHistory()
        service.enqueue(object : Callback<List<TransaksiResponse>>{
            override fun onResponse(
                call: Call<List<TransaksiResponse>>,
                response: Response<List<TransaksiResponse>>
            ) {
                if(response.isSuccessful){
                    _transaksiData.value = response.body()
                    _isLoading.value = false
                }else{
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<List<TransaksiResponse>>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }

        } )
    }
}