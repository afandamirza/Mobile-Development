// HistoryAPIResponse.kt
package com.bangkit.recyclopedia.api.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @field:SerializedName("data")
    val data: List<HistoryItemsResponse>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)