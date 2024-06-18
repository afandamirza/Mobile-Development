package com.bangkit.recyclopedia.api.response

import com.google.gson.annotations.SerializedName

class LoginAppResponse (
    @field:SerializedName("loginResult")
    val loginResult: LoginAppResult?,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

class LoginAppResult (
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("userId")
    val userId: String,

    @field:SerializedName("token")
    val token: String
)