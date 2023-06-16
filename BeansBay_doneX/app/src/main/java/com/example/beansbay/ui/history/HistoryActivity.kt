package com.example.beansbay.ui.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beansbay.adapter.HistoryTransaksiAdapter
import com.example.beansbay.databinding.ActivityHistoryBinding
import com.example.beansbay.network.TransaksiResponse
import com.example.beansbay.ui.checkout.CheckoutActivity
import com.example.beansbay.ui.main.MainActivity
import com.example.beansbay.ui.settings.SettingsActivity

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHistoryBinding
    private val historyViewModel by viewModels<HistoryActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvListHistoryTransaksi.layoutManager = LinearLayoutManager(this)
        supportActionBar?.hide()

        binding.navSetting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.navHomeButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.navCheckoutButton.setOnClickListener {
            startActivity(Intent(this, CheckoutActivity::class.java))
        }

        historyViewModel.getTransaksiHistory()
        historyViewModel.isLoading.observe(this){
            showLoading(it)
        }
        historyViewModel.transaksiData.observe(this){
            setTransaksiData(it)
        }
    }

    private fun showLoading(status: Boolean){
        binding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun setTransaksiData(listTransaksi : List<TransaksiResponse>){
        binding.rvListHistoryTransaksi.adapter = HistoryTransaksiAdapter(listTransaksi)
    }
}