package com.example.beansbay.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import com.example.beansbay.ViewModelFactory
import com.example.beansbay.databinding.ActivityLoginBinding
import com.example.beansbay.model.UserModel
import com.example.beansbay.model.UserPreference
import com.example.beansbay.network.UserLoginRequest
import com.example.beansbay.ui.main.MainActivity
import com.example.beansbay.ui.signup.SignupActivity


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playAnimation()

        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.getUser().observe(this) { user ->
            this.user = user
        }


        binding.navigateToRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            Log.d("email password", "$email dan $password")
            val loginRequest = UserLoginRequest(
                email, password
            )
            loginViewModel.userLogin(loginRequest)
        }

        loginViewModel.loginResult.observe(this){
            if (it == true){
                startActivity(Intent(this, MainActivity::class.java))
            }else{
                Toast.makeText(this@LoginActivity, "Login gagal!", Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.isLoading.observe(this){
            showLoading(it)
        }


    }

    private fun showLoading(status: Boolean){
        binding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.TVtitlelogin, View.ALPHA, 1f).setDuration(500)
        val desk = ObjectAnimator.ofFloat(binding.TVdesk, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.TVteksemaillogin, View.ALPHA, 1f).setDuration(400)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.TVtekspasswordlogin, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                desk,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                login
            )
            startDelay = 500
        }.start()
    }
}