package com.bangkit.recyclopedia.api.response

import com.google.gson.annotations.SerializedName

data class ImagePredictionResponse(
    @SerializedName("status")
    val status: String,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: PredictionData
)

data class PredictionData(
    @SerializedName("id")
    val id: String,

    @SerializedName("result")
    val result: String,

    @SerializedName("congrats")
    val congrats: String,

    @SerializedName("confidenceScore")
    val confidenceScore: Double,

    @SerializedName("createdAt")
    val createdAt: String
)
