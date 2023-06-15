package com.example.beansbay.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beansbay.ViewModelFactory
import com.example.beansbay.adapter.ProductAdapter
import com.example.beansbay.network.Product
import com.example.beansbay.adapter.SpotlightProductAdapter
import com.example.beansbay.databinding.ActivityMainBinding
import com.example.beansbay.model.UserPreference
import com.example.beansbay.network.Results
import com.example.beansbay.ui.checkout.CheckoutActivity
import com.example.beansbay.ui.history.HistoryActivity
import com.example.beansbay.ui.search.SearchActivity
import com.example.beansbay.ui.settings.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var mainViewModel : MainActivityViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[MainActivityViewModel::class.java]


        binding.navSetting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.navCheckoutButton.setOnClickListener{
            startActivity(Intent(this, CheckoutActivity::class.java))
        }

        binding.navHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        binding.rvSpotlightProduct.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        binding.rvRecProduct.layoutManager =
            LinearLayoutManager(
                this,
                LinearLayoutManager.HORIZONTAL,
                false
            )

        mainViewModel.getSpotlightedProduct()
        mainViewModel.spotProduct.observe(this) {
            setSpotProduct(it,mainViewModel.spotProductId.value!!)
        }


        mainViewModel.getRecommendedProduct()
        mainViewModel.simProduct.observe(this) {
            setRecProduct(it,mainViewModel.simProductId.value!!)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }


        binding.buttonSearch.setOnClickListener {
            if (binding.searchBarInput.text.toString() != "" ) {
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("query", binding.searchBarInput.text.toString())
                startActivity(intent)
            } else {
                binding.searchBarInput.error = "You haven't type anything"
            }
        }
    }

    private fun setRecProduct(listProduct : List<Product>, listProductId : List<Results>){
        binding.rvRecProduct.adapter = ProductAdapter(listProduct, listProductId)
    }

    private fun setSpotProduct(listProduct: List<Product>, listProductId : List<Results>){
        binding.rvSpotlightProduct.adapter = SpotlightProductAdapter(listProduct, listProductId)
    }

    private fun showLoading(status: Boolean){
        binding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }
}