package com.example.beansbay.model

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>, context: Context? = null) {
    private val preference = context?.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null

        private val EMAIL_KEY = stringPreferencesKey("email")
        private val PASSWORD_KEY = stringPreferencesKey("password")
        private val TOKEN_KEY = stringPreferencesKey("token")
        private val STATE_KEY = booleanPreferencesKey("state")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[EMAIL_KEY] ?:"",
                preferences[PASSWORD_KEY] ?:"",
                preferences[TOKEN_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }


    suspend fun loginStatus(status : Boolean) {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = status
            if (!status){
                preferences[TOKEN_KEY] = ""
            }
        }
    }

    suspend fun setEmail(email : String){
        dataStore.edit{
            it[EMAIL_KEY] = email
        }
    }

    suspend fun setToken(token:String){
        dataStore.edit{
            it[TOKEN_KEY] = token
        }
    }

}