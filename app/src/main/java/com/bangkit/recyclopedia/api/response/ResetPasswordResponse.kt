package com.bangkit.recyclopedia.api.response

import com.google.gson.annotations.SerializedName

class ResetPasswordResponse (
    @field:SerializedName("error")
    val error: Boolean,
    @field:SerializedName("message")
    val message: String
)