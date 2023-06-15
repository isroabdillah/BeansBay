package com.example.beansbay.ui.login

import android.util.Log
import androidx.lifecycle.*
import com.example.beansbay.model.UserModel
import com.example.beansbay.model.UserPreference
import com.example.beansbay.network.ApiConfig
import com.example.beansbay.network.LoginResponse
import com.example.beansbay.network.UserLoginRequest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult = _loginResult

    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }

    fun loginStatus(status:Boolean) {
        viewModelScope.launch {
            pref.loginStatus(status)
        }
    }

    fun setEmail(email : String){
        viewModelScope.launch{
            pref.setEmail(email)
        }
    }

    fun setData(token : String){
        Log.d("TOKEN", "$token")
        viewModelScope.launch{
            pref.setToken(token)
        }
    }

    fun userLogin(loginRequest : UserLoginRequest){
        _isLoading.value = true
        val service = ApiConfig().getApiService().userLogin(loginRequest)
        service.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if(responseBody?.message == "Login berhasil"){
                    loginStatus(true)
                    setData(responseBody.token)
                    setEmail(loginRequest.email)
                    _loginResult.value = true
                    _isLoading.value = false
                }else{
                    _isLoading.value = false
                    _loginResult.value = false
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }

        })
    }
}