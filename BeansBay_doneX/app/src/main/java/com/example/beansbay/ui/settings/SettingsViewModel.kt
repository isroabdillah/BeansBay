package com.example.beansbay.ui.settings

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beansbay.model.UserPreference
import com.example.beansbay.network.ApiConfig
import com.example.beansbay.network.IsLoggedIn
import com.example.beansbay.network.MessageResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingsViewModel(private val pref : UserPreference) : ViewModel() {

    private val _logoutResult = MutableLiveData<Boolean>()
    val logoutResult = _logoutResult

    fun loginStatus(status:Boolean) {
        viewModelScope.launch {
            Log.d("STATUS LOGIN SETTINGS", status.toString())
            pref.loginStatus(status)
        }
    }



    fun setToken(token : String){
        viewModelScope.launch{
            pref.setToken(token)
        }
    }

    private val _isLoggedIn = MutableLiveData<Boolean>()
    val isLoggedIn = _isLoggedIn

    fun checkLogin(){
        val service = ApiConfig().getApiService().checkLogin()
        service.enqueue(object :Callback<IsLoggedIn>{
            override fun onResponse(call: Call<IsLoggedIn>, response: Response<IsLoggedIn>) {
                if (response.body()?.message == "Pengguna sudah login"){
                    _isLoggedIn.value = true
                }else{
                    _isLoggedIn.value = false
                }
            }

            override fun onFailure(call: Call<IsLoggedIn>, t: Throwable) {
                Log.e("Error: ", "onFailure: ${t.message}")
            }
        })
    }

    fun userLogout(){
        val service = ApiConfig().getApiService().userLogout()
        service.enqueue(object: Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if(response.isSuccessful){
                    setToken("")
                    loginStatus(false)
                    _logoutResult.value = true
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                Log.e("Error: ", "onFailure: ${t.message}")
            }

        })

    }

}