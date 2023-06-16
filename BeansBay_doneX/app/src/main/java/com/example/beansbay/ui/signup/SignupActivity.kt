package com.example.beansbay.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.beansbay.databinding.ActivitySignupBinding
import com.example.beansbay.ui.login.LoginActivity

class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()

        binding.navigateToLogin.setOnClickListener {
            startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
        }

        binding.btnSignupNext.setOnClickListener {
            showLoading(true)
            val username = binding.edRegisterName.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val intent = Intent(this@SignupActivity, Signup2Activity::class.java)
            intent.putExtra("username", username)
            intent.putExtra("email", email)
            intent.putExtra("password", password)
            showLoading(false)
            startActivity(intent)
        }

    }

    private fun showLoading(status: Boolean){
        binding.loading.visibility = if (status) View.VISIBLE else View.GONE
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.TVtitle, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.TVteksnama, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.TVteksemail, View.ALPHA, 1f).setDuration(400)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.TVtekspassword, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.btnSignupNext, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 500
        }.start()
    }


}