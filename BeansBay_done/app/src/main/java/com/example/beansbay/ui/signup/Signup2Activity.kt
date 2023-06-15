package com.example.beansbay.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import com.example.beansbay.R
import com.example.beansbay.databinding.ActivitySignup2Binding
import com.example.beansbay.network.UserRegisterRequest
import com.example.beansbay.ui.login.LoginActivity

class Signup2Activity : AppCompatActivity() {

    private lateinit var binding : ActivitySignup2Binding
    private val signUpViewModel by viewModels<SignUpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignup2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val itemsGender = listOf("Laki-laki", "Perempuan")
        val itemsAroma = listOf("Sangat intens", "Sangat kuat", "Kuat")
        val itemsAsam = listOf("Kuat", "Cukup kuat", "Sedang")
        val itemsKategori = listOf("Arabika", "Robusta","Luwak", "Campuran", "Excelsa", "Catuai", "Liberika", "Single Origin")


        val adapterGender = ArrayAdapter(this, R.layout.item_dropwdown, itemsGender)
        val adapterAroma = ArrayAdapter(this, R.layout.item_dropwdown, itemsAroma)
        val adapterAsam = ArrayAdapter(this, R.layout.item_dropwdown, itemsAsam)
        val adapterKategori = ArrayAdapter(this, R.layout.item_dropwdown, itemsKategori)

        binding.edKelamin.setAdapter(adapterGender)
        binding.edKuatAsam.setAdapter(adapterAsam)
        binding.edAroma.setAdapter(adapterAroma)
        binding.edCategoryCoffe.setAdapter(adapterKategori)

        var kelamin = ""
        var Aroma = ""
        var Asam = ""
        var kategori = ""

        binding.edCategoryCoffe.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            kategori = itemSelected.toString()
        }

        binding.edAroma.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Aroma = itemSelected.toString()
        }

        binding.edKelamin.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            kelamin = itemSelected.toString()
        }

        binding.edKuatAsam.onItemClickListener = AdapterView.OnItemClickListener{
                adapterView, view, i, l ->
            val itemSelected = adapterView.getItemAtPosition(i)
            Asam = itemSelected.toString()
        }


        val username = intent.getStringExtra("username")
        val email = intent.getStringExtra("email")
        val password = intent.getStringExtra("password")

        Log.d("CEK INTENT", "uname: $username, email: $email, password:$password")
        binding.btnSignup.setOnClickListener {
            signUpViewModel.register(
                UserRegisterRequest(
                username!!,
                    email!!,
                    password!!,
                kelamin,
                kategori,
                binding.edKuatAroma.text.toString(),
                Aroma,
                binding.edSkorAsam.text.toString(),
                Asam,
            )
            )
        }

        signUpViewModel.isLoading.observe(this){
            showLoading(it)
        }

        signUpViewModel.registerResult.observe(this){
            if(it == true){
                Toast.makeText(this@Signup2Activity, "Pendaftaran berhasil! Silakan login", Toast.LENGTH_SHORT ).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }else{
                Toast.makeText(this@Signup2Activity, "Register Gagal!", Toast.LENGTH_SHORT ).show()
            }
        }
    }

    private fun showLoading(status: Boolean){
        binding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }
}