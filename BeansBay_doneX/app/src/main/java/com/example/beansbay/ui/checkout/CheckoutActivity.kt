package com.example.beansbay.ui.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beansbay.adapter.CheckoutAdapter
import com.example.beansbay.adapter.CheckoutResultAdapter
import com.example.beansbay.databinding.ActivityCheckoutBinding
import com.example.beansbay.network.CartItems
import com.example.beansbay.ui.history.HistoryActivity
import com.example.beansbay.ui.main.MainActivity
import com.example.beansbay.ui.settings.SettingsActivity

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding

    private val checkoutViewModel by viewModels<CheckoutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listCheckout.layoutManager = LinearLayoutManager(this)
        binding.rvListCheckout.layoutManager = LinearLayoutManager(this)



        binding.navSetting.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.navHomeButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }

        binding.navHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }


//        GET DATA CART USER
        checkoutViewModel.getCartItems()
//        SET DATA CHECKOUT
        checkoutViewModel.listCheckout.observe(this){
            if(it.cartItems.size == 0){
                Toast.makeText(this@CheckoutActivity, "Keranjang Kosong!", Toast.LENGTH_SHORT).show()
                binding.buttonCheckout.text = "Home"
                binding.totalHargaBeforeCheckout.visibility = View.GONE
                binding.buttonCheckout.isEnabled = true
                binding.buttonCheckout.setOnClickListener {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
            else{
                setListCheckout(it.cartItems)
                binding.totalHargaAll.text = "Rp"+it.hargaKeranjang.toString()
                binding.buttonCheckout.isEnabled = true
            }
            }

//        NGURANGIN QTY PRODUK
        checkoutViewModel.statusDelete.observe(this){
            if(it){
                Toast.makeText(this@CheckoutActivity, "Berhasil mengurangi QTY", Toast.LENGTH_SHORT).show()
                finish()
                startActivity(getIntent())
            }
        }

//        SESUDAH CHECKOUT
        binding.buttonCheckout.setOnClickListener {
            checkoutViewModel.checkOut()
        }

        checkoutViewModel.isLoading.observe(this){
            showLoading(it)
        }

        checkoutViewModel.checkOutData.observe(this){
            showLoading(true)
            binding.listCheckout.visibility = View.GONE
            binding.totalHargaBeforeCheckout.visibility = View.GONE
            binding.listResultCheckout.visibility = View.VISIBLE
            binding.buttonCheckout.text = "Home"

            binding.buttonCheckout.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
            }

            binding.invoice.text = "Invoice : "+ it.invoice
            binding.dateTime.text = "Tanggal & Waktu : "+ it.tanggalWaktuCheckout
            binding.totalCheckout.text = "Total Harga : "+ it.hargaKeranjang
            setListCheckoutResult(it.cartItems)
            showLoading(false)
        }
    }

    private fun setListCheckout(listProductCart : List<CartItems>){
        binding.listCheckout.adapter = CheckoutAdapter(
            listProductCart,
            object : CheckoutAdapter.OnAdapterListener{
                override fun onClick(id: String) {
                    checkoutViewModel.deleteProduct(id)
                }
            }
        )
    }

    private fun setListCheckoutResult(listProductCart : List<CartItems>){
        binding.rvListCheckout.adapter = CheckoutResultAdapter(listProductCart)
    }

    private fun showLoading(status: Boolean){
        binding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }

}