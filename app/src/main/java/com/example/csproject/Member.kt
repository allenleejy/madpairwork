package com.example.csproject

import android.text.Editable

data class Member(
    val username: String,
    val password: String,
    var signedIn: Boolean
)