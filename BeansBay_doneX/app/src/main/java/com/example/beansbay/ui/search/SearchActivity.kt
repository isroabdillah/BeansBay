package com.example.beansbay.ui.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beansbay.adapter.SearchAdapter
import com.example.beansbay.databinding.ActivitySearchBinding
import com.example.beansbay.network.Product

class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding
    private val searchActivityViewModel by viewModels<SearchActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val query = intent.getStringExtra("query")
        Log.d("tes data", "$query")

        binding.searchQuery.text = "You search for : $query"

        binding.rvSearchQuery.layoutManager =
            LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)

        searchActivityViewModel.searchProduct(query!!)
        searchActivityViewModel.searchResult.observe(this){
            setSearchResult(it)
        }

        searchActivityViewModel.isLoading.observe(this){
            showLoading(it)
        }
    }

    private fun setSearchResult(listProduct : List<Product>){
        binding.rvSearchQuery.adapter = SearchAdapter(listProduct)
    }

    private fun showLoading(status: Boolean){
        binding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }
}