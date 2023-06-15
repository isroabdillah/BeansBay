package com.example.beansbay.ui.settings

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.beansbay.R
import com.example.beansbay.ViewModelFactory
import com.example.beansbay.databinding.ActivitySettingsBinding
import com.example.beansbay.model.UserPreference
import com.example.beansbay.ui.welcome.WelcomeScreen

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingsBinding
    private lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.namaUser.alpha = 0f

        settingsViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[SettingsViewModel::class.java]

        binding.fotoUser.setImageResource(R.drawable.logobeand)

        binding.rowPref.setOnClickListener {
            Toast.makeText(this@SettingsActivity, "Feature Not Yet Developed!", Toast.LENGTH_SHORT).show()
        }

        binding.rowLogout.setOnClickListener{
            settingsViewModel.userLogout()
        }

        binding.rowHelp.setOnClickListener {
            settingsViewModel.checkLogin()
        }

        binding.rowAccount.setOnClickListener {
            Toast.makeText(this@SettingsActivity, "Feature Not Yet Developed!", Toast.LENGTH_SHORT).show()
        }

        settingsViewModel.isLoggedIn.observe(this){
            if(it){
                Toast.makeText(this@SettingsActivity, "Sudah login", Toast.LENGTH_SHORT).show()
            } else{
                Toast.makeText(this@SettingsActivity, "Kelogout", Toast.LENGTH_SHORT).show()
            }
        }

        settingsViewModel.logoutResult.observe(this){
            if(it){
                val intent =Intent(this, WelcomeScreen::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            }
            else{
                Toast.makeText(this@SettingsActivity, "Logout gagal", Toast.LENGTH_SHORT).show()
            }
        }

    }
}