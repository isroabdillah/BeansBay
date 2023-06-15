package com.example.beansbay.ui.welcome

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.beansbay.ViewModelFactory
import com.example.beansbay.databinding.ActivityWelcomeScreenBinding
import com.example.beansbay.model.UserPreference
import com.example.beansbay.ui.login.LoginActivity
import com.example.beansbay.ui.main.MainActivity
import com.example.beansbay.ui.signup.SignupActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class WelcomeScreen : AppCompatActivity() {

    private lateinit var welcomeScreenViewModel: WelcomeScreenViewModel
    private lateinit var binding : ActivityWelcomeScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        welcomeScreenViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[WelcomeScreenViewModel::class.java]

        binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        welcomeScreenViewModel.checkLogin()

        welcomeScreenViewModel.isLoggedIn.observe(this){
            if(it){
                Toast.makeText(this@WelcomeScreen, "Logged In!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
            } else{
                Toast.makeText(this@WelcomeScreen, "Logged Out!\n  Please Login!", Toast.LENGTH_SHORT).show()
            }
        }

        welcomeScreenViewModel.isLoading.observe(this){
            showLoading(it)
        }

        playAnimation()
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registerButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

    }

    private fun playAnimation(){

        val loginButton = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)
        val registerButton = ObjectAnimator.ofFloat(binding.registerButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply{
            playSequentially(loginButton, registerButton)
            start()
        }
    }

    private fun showLoading(status: Boolean){
        binding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }
}