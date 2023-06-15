package com.example.beansbay.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.beansbay.ViewModelFactory
import com.example.beansbay.adapter.ProductAdapter
import com.example.beansbay.adapter.ReviewAdapter
import com.example.beansbay.network.Product

import com.example.beansbay.databinding.ActivityDetailProductBinding
import com.example.beansbay.model.UserPreference
import com.example.beansbay.network.AddCartRequest
import com.example.beansbay.network.Results
import com.example.beansbay.network.Reviews
import com.example.beansbay.ui.checkout.CheckoutActivity

class DetailProduct : AppCompatActivity() {

    private lateinit var detailProductViewModel : DetailProductViewModel
    private lateinit var binding : ActivityDetailProductBinding
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        detailProductViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[DetailProductViewModel::class.java]


        binding.rvReview.layoutManager =
            LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)
        binding.rvRecProduct.layoutManager =
            LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL,
                false)


        val productId = intent.getStringExtra("product_id")

        detailProductViewModel.getProductDetail(productId!!)

        detailProductViewModel.detailedProduct.observe(this){
            binding.namaProduk.text = it.namaProduk
            Glide.with(binding.fotoProduk)
                .load(it.url)
                .into(binding.fotoProduk)

            binding.namaTokoProduk.text = it.toko
            binding.hargaProduk.text = "Rp" + it.hargaRupiah.toString()
            binding.deskripsiProduk.text = it.deskripsiProduk
            binding.aromaProduk.text = "Aroma Produk : ${it.aroma}"
            binding.skorAroma.text = "Level Aroma : ${it.skorAroma}"
            binding.asamProduk.text = "Keasaman Produk : ${it.asam}"
            binding.skorAsam.text = "Level Keasaman : ${it.skorAsam}"
            binding.kategoriProduk.text = "Kategori Produk : ${it.kategoriProduk}"
            binding.asalProduk.text = "Asal : ${it.asalProduk}"
        }

        detailProductViewModel.reviewProduct.observe(this){
            setReviewProduct(it)
        }

        detailProductViewModel.getRecommendedProduct()
        detailProductViewModel.recProduct.observe(this){
            setSimiliarProduct(it, detailProductViewModel.recProductId.value!!)
        }

        detailProductViewModel.isLoading.observe(this){
            showLoading(it)
        }

        var quantity : Int =  0

        binding.TVKurang.setOnClickListener {
            if( quantity != 0) {
                quantity -= 1
                binding.textView2.text = quantity.toString()
            }
        }

        binding.TVTambah.setOnClickListener {
            quantity += 1
            binding.textView2.text = quantity.toString()
        }



        binding.addToBag.setOnClickListener {
            detailProductViewModel.getUser().observe(this){ user ->
                Log.d("TOKEN DI DETAIL", user.token)
                detailProductViewModel.addToCart(
                    user.token,
                    AddCartRequest(
                        detailProductViewModel.detailedProduct.value!!.id,
                        quantity.toString(),
                    )
                )
            }
        }

        detailProductViewModel.addCartStatus.observe(this){
            if(it){
                Toast.makeText(this@DetailProduct, "Berhasil menambahkan produk ke keranjang!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,CheckoutActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setSimiliarProduct(listProduct : List<Product>, listProductId : List<Results>){
        binding.rvRecProduct.adapter = ProductAdapter(listProduct, listProductId)
    }

    private fun setReviewProduct(listReview : List<Reviews>){
        binding.rvReview.adapter = ReviewAdapter(listReview)
    }

    private fun showLoading(status: Boolean){
        binding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }
}