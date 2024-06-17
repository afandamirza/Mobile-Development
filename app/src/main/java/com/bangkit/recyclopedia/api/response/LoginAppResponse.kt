package com.bangkit.recyclopedia.api.response

import com.google.gson.annotations.SerializedName

//class LoginAppResponse (
//    @field:SerializedName("loginResult")
//    val loginResult: LoginAppResult?,
//
//    @field:SerializedName("error")
//    val error: Boolean,
//
//    @field:SerializedName("message")
//    val message: String
//)
//
//class LoginAppResult (
//    @field:SerializedName("name")
//    val name: String,
//
//    @field:SerializedName("userId")
//    val userId: String,
//
//    @field:SerializedName("token")
//    val token: String
//)

data class LoginResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("userCredential")
    val userCredential: UserCredential?
)

data class UserCredential(
    @field:SerializedName("user")
    val user: User?,

    @field:SerializedName("providerId")
    val providerId: String?,

    @field:SerializedName("_tokenResponse")
    val tokenResponse: TokenResponse?,

    @field:SerializedName("operationType")
    val operationType: String?
)

data class User(
    @field:SerializedName("uid")
    val uid: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("emailVerified")
    val emailVerified: Boolean,

    @field:SerializedName("isAnonymous")
    val isAnonymous: Boolean,

    @field:SerializedName("providerData")
    val providerData: List<ProviderData>?,

    @field:SerializedName("stsTokenManager")
    val stsTokenManager: StsTokenManager?,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("lastLoginAt")
    val lastLoginAt: String,

    @field:SerializedName("apiKey")
    val apiKey: String,

    @field:SerializedName("appName")
    val appName: String
)

data class ProviderData(
    @field:SerializedName("providerId")
    val providerId: String,

    @field:SerializedName("uid")
    val uid: String,

    @field:SerializedName("displayName")
    val displayName: String?,

    @field:SerializedName("email")
    val email: String?,

    @field:SerializedName("phoneNumber")
    val phoneNumber: String?,

    @field:SerializedName("photoURL")
    val photoURL: String?
)

data class StsTokenManager(
    @field:SerializedName("refreshToken")
    val refreshToken: String,

    @field:SerializedName("accessToken")
    val accessToken: String,

    @field:SerializedName("expirationTime")
    val expirationTime: Long
)

data class TokenResponse(
    @field:SerializedName("kind")
    val kind: String,

    @field:SerializedName("localId")
    val localId: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("displayName")
    val displayName: String,

    @field:SerializedName("idToken")
    val idToken: String,

    @field:SerializedName("registered")
    val registered: Boolean,

    @field:SerializedName("refreshToken")
    val refreshToken: String,

    @field:SerializedName("expiresIn")
    val expiresIn: String
)