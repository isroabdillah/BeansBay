package com.example.beansbay.ui.signup

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beansbay.network.ApiConfig
import com.example.beansbay.network.MessageResponse
import com.example.beansbay.network.UserRegisterRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _registerResult = MutableLiveData<Boolean>()
    val registerResult = _registerResult

    fun register(registerRequest: UserRegisterRequest){
        _isLoading.value = true
        val service = ApiConfig().getApiService().userRegister(registerRequest)
        service.enqueue(object: Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                val responseBody = response.body()
                if (responseBody?.message == "Pendaftaran berhasil"){
                    _registerResult.value = true
                }else{
                    _registerResult.value = false
                }
                _isLoading.value = false
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }

        })
    }

}