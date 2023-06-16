package com.example.beansbay.network

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

data class MessageResponse(
    @field:SerializedName("message")
    val message : String,
)

data class LoginResponse(
    @field:SerializedName("message")
    val message : String,

    @field:SerializedName("token")
    val token : String
)

data class IsLoggedIn(
    @field:SerializedName("loggedIn")
    val loggedIn : Boolean ?= null,

    @field:SerializedName("message")
    val message : String
)

data class Product(
    @field:SerializedName("namaProduk")
    val namaProduk: String,

    @field:SerializedName("asam")
    val asam: String,

//    nullable
    @field:SerializedName("reviews")
    val reviews : ArrayList<String>? = arrayListOf(),

    @field:SerializedName("skorAroma")
    val skorAroma: Int,

    @field:SerializedName("asalProduk")
    val asalProduk: String,

    @field:SerializedName("skorAsam")
    val skorAsam: Int,

    @field:SerializedName("hargaRupiah")
    val hargaRupiah: Double,

    @field:SerializedName("hargaDollar")
    val hargaDollar: Double,

    @field:SerializedName("kategoriProduk")
    val kategoriProduk: String,

    @field:SerializedName("url")
    var url: String,

    @field:SerializedName("aroma")
    var aroma: String ?= null,

    @field:SerializedName("deskripsiProduk")
    var deskripsiProduk: String,

    @field:SerializedName("toko")
    var toko: String,

    @field:SerializedName("id")
    var id: String ?= null,
)

data class Reviews(
    @field:SerializedName("id"        ) var id        : String?    = null,
    @field:SerializedName("createdAt" ) var createdAt : CreatedAt? = null,
    @field:SerializedName("rating"    ) var rating    : Int?       = null,
    @field:SerializedName("comment"   ) var comment   : String?    = null,
    @field:SerializedName("userId"    ) var userId    : String?    = null

)

data class CreatedAt (
    @field:SerializedName("_seconds"     ) var Seconds     : Int? = null,
    @field:SerializedName("_nanoseconds" ) var Nanoseconds : Int? = null
)

data class CartItems (
    @SerializedName("quantity"   ) var quantity   : Int?    = null,
    @SerializedName("productId"  ) var productId  : String? = null,
    @SerializedName("namaProduk" ) var namaProduk     : String? = null,
    @SerializedName("userId"     ) var userId     : String? = null,
    @SerializedName("totalHarga" ) var totalHarga : Int?    = null,
    @SerializedName("url"        ) var url        : String? = null
)

data class Cart(
    @field:SerializedName("cartItems"      )
    var cartItems      : ArrayList<CartItems> = arrayListOf(),

    @field:SerializedName("hargaKeranjang" )
    var hargaKeranjang : Int ?= null
)

data class CheckoutCartResponse(
    @field:SerializedName("message")
    var message : String ?= null,

    @field:SerializedName("cartItems"      )
    var cartItems      : ArrayList<CartItems> = arrayListOf(),

    @field:SerializedName("hargaKeranjang" )
    var hargaKeranjang : Int ?= null,

    @field:SerializedName("tanggalWaktuCheckout")
    var tanggalWaktuCheckout : String ?= null,

    @field:SerializedName("invoice")
    var invoice : String ?= null,
)

data class Results (
    @SerializedName("predicted_idProduk") var predictedIdProduk : String? = null
)

data class ProductRecommendationResponse(
    @SerializedName("results"  ) var results  : ArrayList<Results>,
    @SerializedName("products" ) var products : ArrayList<Product>
)

data class ProductSimiliarResponse (
    @SerializedName("similar"  ) var similar  : ArrayList<Results>,
    @SerializedName("products" ) var products : ArrayList<Product>

)

data class ProductDetailResponse(
    @field:SerializedName("id")
    val id : String,

    @field:SerializedName("asam")
    val asam: String,

    @field:SerializedName("namaProduk")
    val namaProduk: String,

    @field:SerializedName("skorAroma")
    val skorAroma: Int,

    @field:SerializedName("asalProduk")
    val asalProduk: String,

    @field:SerializedName("skorAsam")
    val skorAsam: Int,

    @field:SerializedName("hargaDollar")
    val hargaDollar: Double,

    @field:SerializedName("aroma")
    val aroma: String,

    @field:SerializedName("deskripsiProduk")
    val deskripsiProduk: String,

    @field:SerializedName("kategoriProduk")
    val kategoriProduk: String,

    @field:SerializedName("url")
    val url: String,

    @field:SerializedName("hargaRupiah")
    val hargaRupiah: Int,

    @field:SerializedName("reviews")
    val reviews : List<Reviews>? = null,

    @field:SerializedName("toko")
    val toko : String
)

data class TransaksiResponse(
    @SerializedName("hargaKeranjang"       ) var hargaKeranjang       : Int?                 = null,
    @SerializedName("invoice"              ) var invoice              : String?              = null,
    @SerializedName("cartItems"            ) var cartItems            : ArrayList<CartItems> = arrayListOf(),
    @SerializedName("userId"               ) var userId               : String?              = null,
    @SerializedName("tanggalWaktuCheckout" ) var tanggalWaktuCheckout : String?              = null

)

data class UserRegisterRequest(
    @field:SerializedName("username")
    var username: String,
    @field:SerializedName("email")
    var email          : String,
    @field:SerializedName("password")
    var password       : String,
    @field:SerializedName("jenisKelamin")
    var jenisKelamin   : String,
    @field:SerializedName("kategoriProduk")
    var kategoriProduk : String,
    @field:SerializedName("skorAroma")
    var skorAroma      : String,
    @field:SerializedName("Aroma")
    var Aroma          : String,
    @field:SerializedName("skorAsam")
    var skorAsam       : String,
    @field:SerializedName("asam")
    var asam           : String,
)

data class UserLoginRequest(
    @field:SerializedName("email")
    var email          : String,
    @field:SerializedName("password")
    var password       : String,
)

data class AddCartRequest(
    @field:SerializedName("productId")
    val productId : String,

    @field:SerializedName("quantity")
    val quantity : String
)

interface ApiService {
    @POST("register")
    fun userRegister(
        @Body userRegister : UserRegisterRequest
    ) : Call<MessageResponse>

    @POST("login")
    fun userLogin(
        @Body userLogin : UserLoginRequest
    ): Call<LoginResponse>

    @POST("logout")
    fun userLogout(
    ): Call<MessageResponse>

    @GET("products")
    fun getAllProducts(
    ): Call<List<Product>>

    @GET("products/{id}")
    fun getProductDetails(
        @Path("id") id : String
    ):Call<ProductDetailResponse>

    @GET("products/search")
    fun searchProduct(
        @Query("name") query : String
    ): Call<List<Product>>

    @POST("cart")
    fun addCart(
        @Header("Authorization") token: String,
        @Body addCartRequest : AddCartRequest
    ) : Call<MessageResponse>

    @GET("check-login")
    fun checkLogin(
    ) : Call<IsLoggedIn>

    @GET("cart")
    fun getCart(
    ): Call<Cart>

    @DELETE("cart/{id}")
    fun deleteProduct(
        @Path("id") id : String
    ) : Call<MessageResponse>

    @POST("cart/checkout")
    fun checkoutCart(
    ) : Call<CheckoutCartResponse>

    @GET("transaksi")
    fun getTransaksiHistory(
    ): Call<List<TransaksiResponse>>

    @GET("recomendation")
    fun getRecommendedProduct(
    ): Call<ProductRecommendationResponse>

    @GET("similar")
    fun getSimiliarProduct(
    ): Call<ProductSimiliarResponse>
}

