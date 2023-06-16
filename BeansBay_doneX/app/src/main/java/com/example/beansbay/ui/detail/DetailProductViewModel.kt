package com.example.beansbay.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.beansbay.model.UserModel
import com.example.beansbay.model.UserPreference
import com.example.beansbay.network.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailProductViewModel(private val pref : UserPreference) : ViewModel() {


    fun getUser(): LiveData<UserModel> {
        return pref.getUser().asLiveData()
    }



    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading = _isLoading

    private val _detailedProduct = MutableLiveData<ProductDetailResponse>()
    val detailedProduct = _detailedProduct

    private val _reviewProduct = MutableLiveData<List<Reviews>>()
    val reviewProduct = _reviewProduct

    private val _recProduct = MutableLiveData<List<Product>>()
    val recProduct = _recProduct

    private val _recProductId = MutableLiveData<List<Results>>()
    val recProductId = _recProductId

    private val _addCartStatus = MutableLiveData<Boolean>()
    val addCartStatus = _addCartStatus

    fun addToCart(token : String, addCartRequest: AddCartRequest){
        _isLoading.value = true
        val service = ApiConfig().getApiService().addCart("Bearer $token", addCartRequest)
        service.enqueue(object :  Callback<MessageResponse>{
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if(response.isSuccessful){
                    _addCartStatus.value = true
                }else{
                    _addCartStatus.value = false
                }
               }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
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
                    _recProductId.value = response.body()!!.similar
                    _recProduct.value = response.body()!!.products
                    _isLoading.value = false
                }
            }

            override fun onFailure(call: Call<ProductSimiliarResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }

        })
    }

    fun getProductDetail(id : String){
        _isLoading.value = true
        val service = ApiConfig().getApiService().getProductDetails(id)
        service.enqueue(object: Callback<ProductDetailResponse>{
            override fun onResponse(
                call: Call<ProductDetailResponse>,
                response: Response<ProductDetailResponse>
            ) {
                _isLoading.value = false
                _detailedProduct.value = response.body()
                Log.d("DetailProductViewModel", response.body().toString())
                _reviewProduct.value = response.body()?.reviews!!
            }

            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Error: ", "onFailure: ${t.message}")
            }

        })
    }

    fun getProductReview(id: String){
////     panggil api review produk berdasarkan id produk
////     sementara pakek dummy dulu
//        _reviewProduct.value = getDummyReview()
    }
}