package com.bangkit.recyclopedia.api.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "history_items")
class HistoryItemsResponse (
    @PrimaryKey
    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("result")
    val result: String,

    @field:SerializedName("createdAt")
    val createdAt: String,

    @field:SerializedName("confidenceScore")
    val confidenceScore: Double,

    @field:SerializedName("poin")
    val poin: Int,
)


