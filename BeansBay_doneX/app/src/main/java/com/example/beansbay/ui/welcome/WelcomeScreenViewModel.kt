package com.example.beansbay.ui.welcome

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.beansbay.model.UserModel
import com.example.beansbay.model.UserPreference
import com.example.beansbay.network.ApiConfig
import com.example.beansbay.network.IsLoggedIn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeScreenViewModel(private val pref : UserPreference) : ViewModel() {
    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn = _isLoggedIn

    fun checkLogin(){
        _isLoading.value = true
        val service = ApiConfig().getApiService().checkLogin()
        service.enqueue(object : Callback<IsLoggedIn> {
            override fun onResponse(call: Call<IsLoggedIn>, response: Response<IsLoggedIn>) {
                if (response.body()?.message == "Pengguna sudah login"){
                    _isLoggedIn.value = true
                }else{
                    _isLoggedIn.value = false
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<IsLoggedIn>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }
        })
    }

}
