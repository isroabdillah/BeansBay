package com.example.beansbay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.beansbay.model.UserPreference
import com.example.beansbay.ui.detail.DetailProductViewModel
import com.example.beansbay.ui.login.LoginViewModel
import com.example.beansbay.ui.main.MainActivityViewModel
import com.example.beansbay.ui.settings.SettingsViewModel
import com.example.beansbay.ui.welcome.WelcomeScreenViewModel


class ViewModelFactory(private val pref: UserPreference) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                MainActivityViewModel(pref) as T
            }

            modelClass.isAssignableFrom(DetailProductViewModel::class.java) -> {
                DetailProductViewModel(pref) as T
            }

            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(pref) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }

            modelClass.isAssignableFrom(WelcomeScreenViewModel::class.java) -> {
                WelcomeScreenViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}