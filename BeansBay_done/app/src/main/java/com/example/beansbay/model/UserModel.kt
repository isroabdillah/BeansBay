package com.example.beansbay.model

data class UserModel(
    val email: String,
    val password: String,
    var token : String,
    val isLogin: Boolean
)